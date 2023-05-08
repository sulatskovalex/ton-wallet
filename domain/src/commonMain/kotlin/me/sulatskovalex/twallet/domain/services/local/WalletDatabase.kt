package me.sulatskovalex.twallet.domain.services.local

import me.sulatskovalex.twallet.domain.models.Wallet

interface WalletDatabase {
    suspend fun insertWallet(wallet: Wallet)
    suspend fun selectMainWallet(): Wallet?
    suspend fun selectWallets(): List<Wallet>
    suspend fun selectMainWalletAddress(): String?
    suspend fun updateWallet(accountAddressHex: String, amount: Long, )
    suspend fun deleteWallet()
}
