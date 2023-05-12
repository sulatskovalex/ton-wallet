package me.sulatskovalex.twallet.domain.services.remote

import me.sulatskovalex.twallet.domain.models.Wallet

interface TonClient {
    suspend fun randomWords(): List<String>
    suspend fun generateWords(): List<String>
    suspend fun createWallet(words: List<String>): Wallet
    fun validateAddress(scanned: String): String?
}