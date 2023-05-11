package me.sulatskovalex.twallet.domain.models

class WalletInfo(
    val address: String = "",
    val amount: String = "",
    val shortAddress: String =
        if (address.isNotEmpty())
            "${address.substring(0, 4)}...${address.substring(address.length - 4)}"
        else
            ""
)