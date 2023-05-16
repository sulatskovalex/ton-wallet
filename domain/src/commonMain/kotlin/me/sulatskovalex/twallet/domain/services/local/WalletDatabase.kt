package me.sulatskovalex.twallet.domain.services.local

import me.sulatskovalex.twallet.domain.models.Wallet

interface WalletDatabase {
    suspend fun insertWallet(wallet: Wallet)
    suspend fun selectWallet(): Wallet?
    suspend fun selectWallets(): List<Wallet>
    suspend fun updateWallet(amount: Long)
    suspend fun deleteCurrentWallet()
}
