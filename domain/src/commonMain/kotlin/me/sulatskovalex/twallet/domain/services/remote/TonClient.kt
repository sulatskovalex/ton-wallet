package me.sulatskovalex.twallet.domain.services.remote

import me.sulatskovalex.twallet.domain.models.Wallet

interface TonClient {
    suspend fun generateWords(): List<String>
    suspend fun generateWords(words: List<String>): Wallet
}