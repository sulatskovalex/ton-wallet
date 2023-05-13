package me.sulatskovalex.twallet.screens

import com.adeo.kviewmodel.KViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.sulatskovalex.twallet.domain.repositories.WalletRepository

class SplashViewModel(
    private val walletRepository: WalletRepository,
) : KViewModel() {

    fun onLaunch(onGoToStart: () -> Unit, onGoToHome: () -> Unit) {
        viewModelScope.launch {
            listOf(
                async {
                    delay(1000)
                    false
                },
                async {
                    walletRepository.isAnyWalletExists()
                }).awaitAll()
                .contains(true)
                .let {
                    if (it) {
                        onGoToHome.invoke()
                    } else {
                        onGoToStart.invoke()
                    }
                }
        }
    }
}