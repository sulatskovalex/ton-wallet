package me.sulatskovalex.twallet.domain.repositories

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import me.sulatskovalex.twallet.domain.models.WalletInfo
import me.sulatskovalex.twallet.domain.services.local.WalletDatabase
import me.sulatskovalex.twallet.domain.services.remote.NetworkSwitcher
import me.sulatskovalex.twallet.domain.services.remote.NetworkSwitcherListener
import me.sulatskovalex.twallet.domain.services.remote.TonClient

class WalletRepository(
    private val tonClient: TonClient,
    private val walletDatabase: WalletDatabase,
    networkSwitcher: NetworkSwitcher,
) : NetworkSwitcherListener {
    init {
        networkSwitcher.watch(this)
    }

    private val scope = CoroutineScope(CoroutineName("WalletRepository") + SupervisorJob())
    private val _walletInfo = MutableStateFlow(WalletInfo())
    val walletInfo: SharedFlow<WalletInfo> = flow {
        _walletInfo.collect {
            val address = validateAddress(it.address) ?: it.address
            emit(it.copy(address = address))
        }
    }.shareIn(scope, SharingStarted.WhileSubscribed(), 1)

    suspend fun generateWords(): List<String> =
        tonClient.generateWords()

    suspend fun randomWords(): List<String> =
        tonClient.randomWords()

    suspend fun createWallet(words: List<String>) {
        val wallet = tonClient.createWallet(words)
        _walletInfo.emit(
            WalletInfo(
                address = wallet.accountAddressFriendly.withCurrentSettings(),
                amount = wallet.amount,
            )
        )
        walletDatabase.insertWallet(wallet)
    }

    suspend fun isAnyWalletExists(): Boolean {
        val wallet = walletDatabase.selectWallet()
        if (wallet != null) {
            _walletInfo.emit(
                WalletInfo(
                    address = wallet.accountAddressFriendly.withCurrentSettings(),
                    amount = wallet.amount,
                )
            )
            return true
        }
        return false
    }

    suspend fun deleteCurrentWallet() {
        walletDatabase.deleteCurrentWallet()
    }

    fun mnemonicWords(): List<String> =
        tonClient.mnemonicWords()

    fun validateAddress(scanned: String): String? =
        tonClient.validateAddress(scanned)


    fun updateWalletInfo() {
        scope.launch {
            walletDatabase.selectWallet()?.let { wallet ->
                _walletInfo.emit(
                    WalletInfo(
                        address = wallet.accountAddressFriendly.withCurrentSettings(),
                        amount = wallet.amount,
                        isLoading = true,
                    ),
                )
                tonClient.getAccountInfo(
                    wallet.accountAddressFriendly.withCurrentSettings(),
                ).onSuccess {
                    walletDatabase.updateWallet(it.amount)
                    _walletInfo.emit(it)
                }.onFailure {
                    walletDatabase.updateWallet(0)
                    _walletInfo.emit(
                        WalletInfo(
                            address = wallet.accountAddressFriendly.withCurrentSettings(),
                            amount = 0,
                            isLoading = false
                        ),
                    )
                }
            }
        }
    }

    override fun onNetworkChanged(isTestnet: Boolean) {
        _walletInfo.value = _walletInfo.value.copy(amount = 0)
        scope.launch {
            walletDatabase.updateWallet(0L)
            updateWalletInfo()
        }
    }

    private fun String.withCurrentSettings(): String = validateAddress(this) ?: this

}
