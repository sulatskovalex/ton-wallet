package me.sulatskovalex.twallet.domain.di

import me.sulatskovalex.twallet.domain.services.remote.NetworkSwitcher
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect fun createNetworkSwitcher(): NetworkSwitcher

fun domainModule(): Module = module {
    single<NetworkSwitcher> { createNetworkSwitcher() }
}
