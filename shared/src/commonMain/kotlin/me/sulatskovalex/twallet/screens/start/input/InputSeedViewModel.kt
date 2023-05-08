package me.sulatskovalex.twallet.screens.start.input

import com.adeo.kviewmodel.KViewModel
import me.sulatskovalex.twallet.domain.repositories.WalletRepository

class InputSeedViewModel(
    private val walletRepository: WalletRepository,
): KViewModel() {
}