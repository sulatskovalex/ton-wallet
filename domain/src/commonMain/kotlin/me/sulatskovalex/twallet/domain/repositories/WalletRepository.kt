package me.sulatskovalex.twallet.domain.repositories

import me.sulatskovalex.twallet.domain.services.local.WalletDatabase
import me.sulatskovalex.twallet.domain.services.remote.TonClient

class WalletRepository(
    private val tonClient: TonClient,
    private val walletDatabase: WalletDatabase,
) {

    suspend fun generateWords(): List<String> =
        tonClient.generateWords()

    suspend fun randomWords(): List<String> =
        tonClient.randomWords()

    suspend fun createWallet(words: List<String>) {
        val wallet = tonClient.createWallet(words)
        walletDatabase.insertWallet(wallet)
    }

    suspend fun isAnyWalletExists(): Boolean =
        !walletDatabase.selectMainWalletAddress().isNullOrEmpty()

    suspend fun deleteCurrentWallet() {
        walletDatabase.deleteCurrentWallet()
    }
}
