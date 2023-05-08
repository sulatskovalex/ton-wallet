package me.sulatskovalex.twallet.screens

import com.adeo.kviewmodel.KViewModel
import kotlinx.coroutines.delay
import me.sulatskovalex.twallet.domain.repositories.WalletRepository

class SplashViewModel(
    val walletRepository: WalletRepository,
) : KViewModel() {
    suspend inline fun onLaunch(onGoToStart: () -> Unit, onGoToHome: () -> Unit) {
        delay(1000)
        if (walletRepository.isAnyWalletExists()) {
            onGoToHome.invoke()
        } else {
            onGoToStart.invoke()
        }
    }

}