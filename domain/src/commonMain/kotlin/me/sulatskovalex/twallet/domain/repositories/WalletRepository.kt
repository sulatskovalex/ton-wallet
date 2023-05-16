package me.sulatskovalex.twallet.domain.repositories

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import me.sulatskovalex.twallet.domain.format
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
                address = wallet.accountAddressFriendly,
                amount = wallet.amount.format()
            )
        )
        walletDatabase.insertWallet(wallet)
    }

    suspend fun isAnyWalletExists(): Boolean {
        val wallet = walletDatabase.selectMainWallet()
        if (wallet != null) {
            _walletInfo.emit(
                WalletInfo(
                    address = wallet.accountAddressFriendly,
                    amount = wallet.amount.format()
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


    suspend fun updateWalletInfo() {
        walletDatabase.selectMainWallet()?.let { wallet ->
            _walletInfo.emit(
                WalletInfo(
                    address = wallet.accountAddressFriendly,
                    amount = wallet.amount.format(),
                    isLoading = true,
                ),
            )
            tonClient.getAccountInfo(
                wallet.accountAddressFriendly,
            ).onSuccess {
                _walletInfo.emit(it)
            }.onFailure {
                _walletInfo.emit(
                    WalletInfo(
                        address = wallet.accountAddressFriendly,
                        amount = wallet.amount.format(),
                    ),
                )
            }
        }
    }

    override fun onNetworkChanged(isTestnet: Boolean) {
        _walletInfo.value = _walletInfo.value.copy()
    }

}
