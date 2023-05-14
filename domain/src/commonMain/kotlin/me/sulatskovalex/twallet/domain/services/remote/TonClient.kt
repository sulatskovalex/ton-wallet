package me.sulatskovalex.twallet.domain.services.remote

import me.sulatskovalex.twallet.domain.models.Wallet
import me.sulatskovalex.twallet.domain.models.WalletInfo

interface TonClient {
    suspend fun randomWords(): List<String>
    suspend fun generateWords(): List<String>
    suspend fun createWallet(words: List<String>): Wallet
    fun validateAddress(scanned: String): String?
    suspend fun getAccountInfo(address: String): Result<WalletInfo>
}