package me.sulatskovalex.twallet.remote

import me.sulatskovalex.twallet.domain.models.Wallet
import me.sulatskovalex.twallet.domain.services.remote.NetworkSwitcher
import me.sulatskovalex.twallet.domain.services.remote.TonClient
import org.ton.api.pub.PublicKeyEd25519
import org.ton.block.AddrStd
import org.ton.contract.wallet.WalletV4R2Contract
import org.ton.crypto.Ed25519
import org.ton.mnemonic.Mnemonic

internal class TonClientImpl(
    private val networkSwitcher: NetworkSwitcher
) : TonClient {

    override suspend fun generateWords(): List<String> =
        Mnemonic.generate()

    override suspend fun generateWords(words: List<String>): Wallet {
        val privateKey = Mnemonic.toSeed(words)
        val publicKey = Ed25519.publicKey(privateKey)
        val wallet = WalletV4R2Contract(0, PublicKeyEd25519(publicKey))
        val addressStd = (wallet.address as AddrStd)
        val accountAddressFriendly = addressStd.toString(
            userFriendly = true,
            testOnly = networkSwitcher.isTestnet
        )
        return Wallet(
            accountAddressHex = addressStd.address.toHex(),
            accountAddressFriendly = accountAddressFriendly,
            privateKey = privateKey,
            publicKey = publicKey,
            amount = 0L,
            isMain = true,
        )
    }
}
