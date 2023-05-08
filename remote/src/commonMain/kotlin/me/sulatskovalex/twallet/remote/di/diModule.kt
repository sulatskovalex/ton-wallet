package me.sulatskovalex.twallet.remote.di

import me.sulatskovalex.twallet.domain.services.remote.TonClient
import me.sulatskovalex.twallet.remote.TonClientImpl
import org.koin.core.module.Module
import org.koin.dsl.module

fun remoteModule(): Module = module {
    single<TonClient> { TonClientImpl(get()) }
}
