package me.sulatskovalex.twallet.di

import me.sulatskovalex.twallet.database.di.databaseModule
import me.sulatskovalex.twallet.domain.di.domainModule
import me.sulatskovalex.twallet.domain.repositories.WalletRepository
import me.sulatskovalex.twallet.remote.di.remoteModule
import me.sulatskovalex.twallet.screens.SplashViewModel
import me.sulatskovalex.twallet.screens.home.home.assets.AssetsViewModel
import me.sulatskovalex.twallet.screens.home.settings.SettingsViewModel
import me.sulatskovalex.twallet.screens.start.StartViewModel
import me.sulatskovalex.twallet.screens.start.create.CreateWalletViewModel
import me.sulatskovalex.twallet.screens.start.input.InputSeedViewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module

object TWalletDI {

    fun startDI() = startDI(*emptyArray())

    fun startDI(vararg modules: Module) =
        startKoin {
            modules(*modules)
            modules(
                domainModule(),
                databaseModule(),
                remoteModule(),
            )
            modules(
                module {
                    single { WalletRepository(get(), get()) }
                    factory { SplashViewModel(get()) }
                    factory { StartViewModel() }
                    factory { InputSeedViewModel(get()) }
                    factory { CreateWalletViewModel(get()) }
                    factory { AssetsViewModel(get()) }
                    factory { SettingsViewModel(get()) }
                },
            )
        }

    fun stopDI() {
        stopKoin()
    }
}