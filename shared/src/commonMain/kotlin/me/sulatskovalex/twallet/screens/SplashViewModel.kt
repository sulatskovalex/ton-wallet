package me.sulatskovalex.twallet.screens

import com.adeo.kviewmodel.KViewModel
import kotlinx.coroutines.delay

class SplashViewModel : KViewModel() {
    suspend inline fun onLaunch(onGoToStart: () -> Unit, onGoToHome: () -> Unit) {
        delay(1000)
        if (true) {
            onGoToStart.invoke()
        } else {
            onGoToHome.invoke()
        }
    }

}