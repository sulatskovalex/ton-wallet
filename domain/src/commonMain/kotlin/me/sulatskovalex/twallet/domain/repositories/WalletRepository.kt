package me.sulatskovalex.twallet.domain.repositories

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import me.sulatskovalex.twallet.domain.models.WalletInfo
import me.sulatskovalex.twallet.domain.format
import me.sulatskovalex.twallet.domain.services.local.WalletDatabase
import me.sulatskovalex.twallet.domain.services.remote.TonClient

class WalletRepository(
    private val tonClient: TonClient,
    private val walletDatabase: WalletDatabase,
) {
    private val _walletInfo = MutableSharedFlow<WalletInfo>(1, 1, BufferOverflow.DROP_OLDEST)
    val walletInfo: SharedFlow<WalletInfo>
        get() = _walletInfo.asSharedFlow()

    suspend fun generateWords(): List<String> =
        tonClient.generateWords()

    suspend fun randomWords(): List<String> =
        tonClient.randomWords()

    suspend fun createWallet(words: List<String>) {
        val wallet = tonClient.createWallet(words)
        _walletInfo.emit(WalletInfo(wallet.accountAddressFriendly, wallet.amount.format()))
        walletDatabase.insertWallet(wallet)
    }

    suspend fun isAnyWalletExists(): Boolean {
        val wallet = walletDatabase.selectMainWallet()
        if (wallet != null) {
            _walletInfo.emit(WalletInfo(wallet.accountAddressFriendly, wallet.amount.format()))
            return true
        }
        return false
    }

    suspend fun deleteCurrentWallet() {
        walletDatabase.deleteCurrentWallet()
    }

    fun validateAddress(scanned: String): String? =
        tonClient.validateAddress(scanned)

}
