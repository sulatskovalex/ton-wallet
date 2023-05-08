package me.sulatskovalex.twallet.domain.repositories

import me.sulatskovalex.twallet.domain.services.local.WalletDatabase
import me.sulatskovalex.twallet.domain.services.remote.TonClient

class WalletRepository(
    private val tonClient: TonClient,
    private val walletDatabase: WalletDatabase,
) {

    suspend fun generateWords(): List<String> =
        tonClient.generateWords()

    suspend fun generateWords(words: List<String>) {
        val wallet = tonClient.generateWords(words)
        walletDatabase.insertWallet(wallet)
    }

    suspend fun isAnyWalletExists(): Boolean =
        walletDatabase.selectMainWalletAddress() != null

    suspend fun exitWallet() {
        walletDatabase.deleteWallet()
    }
}
