package me.sulatskovalex.twallet.domain.di

import me.sulatskovalex.twallet.domain.services.remote.NetworkSwitcher

internal actual fun createNetworkSwitcher(): NetworkSwitcher =
    NetworkSwitcher(inject())
