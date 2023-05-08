package me.sulatskovalex.twallet.domain.models

data class Wallet(
    val accountAddressHex: String,
    val accountAddressFriendly: String,
    val amount: Long,
    val privateKey: ByteArray,
    val publicKey: ByteArray,
    val isMain: Boolean,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Wallet

        if (accountAddressHex != other.accountAddressHex) return false
        return true
    }

    override fun hashCode(): Int =
        accountAddressHex.hashCode()
}
