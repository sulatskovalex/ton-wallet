package me.sulatskovalex.twallet.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.sulatskovalex.twallet.domain.models.Wallet
import me.sulatskovalex.twallet.domain.services.local.WalletDatabase

class WalletDatabaseImpl(
    private val walletsDao: WalletsDaoQueries,
) : WalletDatabase {

    override suspend fun insertWallet(wallet: Wallet) {
        withContext(Dispatchers.Default) {
            walletsDao.insertWallet(
                accountAddressHex = wallet.accountAddressHex,
                accountAddressFriendly = wallet.accountAddressFriendly,
                amount = wallet.amount,
                privateKey = wallet.privateKey,
                publicKey = wallet.publicKey,
                isMain = if (wallet.isMain) 1 else 0,
            )
        }
    }

    override suspend fun selectWallet(): Wallet? =
        withContext(Dispatchers.Default) {
            walletsDao.selectMainWallet().executeAsOneOrNull()?.let { w ->
                Wallet(
                    accountAddressHex = w.accountAddressHex,
                    accountAddressFriendly = w.accountAddressFriendly,
                    amount = w.amount,
                    privateKey = w.privateKey,
                    publicKey = w.publicKey,
                    isMain = w.isMain == 1L,
                )
            }
        }

    override suspend fun selectWallets(): List<Wallet> =
        withContext(Dispatchers.Default) {
            walletsDao.selectWallets().executeAsList().map { w ->
                Wallet(
                    accountAddressHex = w.accountAddressHex,
                    accountAddressFriendly = w.accountAddressFriendly,
                    amount = w.amount,
                    privateKey = w.privateKey,
                    publicKey = w.publicKey,
                    isMain = w.isMain == 1L,
                )
            }
        }

    override suspend fun updateWallet(amount: Long) {
        withContext(Dispatchers.Default) {
            walletsDao.updateMainWallet(amount)
        }
    }

    override suspend fun deleteCurrentWallet() {
        withContext(Dispatchers.Default) {
            walletsDao.selectMainWallet().executeAsOneOrNull()?.let { w ->
                walletsDao.deleteWallet(w.accountAddressHex)
            }
        }
    }
}
