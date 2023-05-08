package me.sulatskovalex.twallet.di

import me.sulatskovalex.twallet.screens.SplashViewModel
import me.sulatskovalex.twallet.screens.start.create.CreateWalletViewModel
import me.sulatskovalex.twallet.screens.start.input.InputSeedViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.mp.KoinPlatformTools

object TWalletDI {

    fun startDI() = startDI(emptyList())

    fun startDI(modules: List<Module>) =
        startKoin {
            modules(
                module {
                    factory { SplashViewModel() }
                    factory { InputSeedViewModel() }
                    factory { CreateWalletViewModel() }
                },
            )
            modules(modules)
        }

    fun stopDI() {
        KoinPlatformTools.defaultContext().stopKoin()
    }
}

inline fun <reified T> inject(): T =
    KoinPlatformTools.defaultContext().get().get()
