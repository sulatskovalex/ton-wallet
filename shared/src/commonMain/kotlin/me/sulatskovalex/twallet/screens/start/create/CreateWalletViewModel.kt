package me.sulatskovalex.twallet.screens.start.create

import com.adeo.kviewmodel.KViewModel
import me.sulatskovalex.twallet.domain.repositories.WalletRepository

class CreateWalletViewModel(
    private val walletRepository: WalletRepository,
) : KViewModel() {

}