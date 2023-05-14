package me.sulatskovalex.twallet.remote.di

import io.ktor.client.engine.HttpClientEngine
import me.sulatskovalex.twallet.domain.services.remote.TonClient
import me.sulatskovalex.twallet.remote.TonClientImpl
import me.sulatskovalex.twallet.remote.TonLiteClient
import org.koin.core.module.Module
import org.koin.dsl.module

fun remoteModule(): Module = module {
    single<TonClient> { TonClientImpl(get(), get()) }
    single { createEngine() }
    single { TonLiteClient(get(), get()) }
}

expect fun createEngine(): HttpClientEngine
