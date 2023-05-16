package me.sulatskovalex.twallet.domain.models

import me.sulatskovalex.twallet.domain.format

data class WalletInfo(
    val address: String = "",
    val amount: Long = 0L,
    val isLoading: Boolean = false,
) {
    val amountFormatted: String
        get() = amount.format()

    val shortAddress: String =
        if (address.isNotEmpty())
            "${address.substring(0, 4)}...${address.substring(address.length - 4)}"
        else
            ""
}
