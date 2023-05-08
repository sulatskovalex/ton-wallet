package me.sulatskovalex.twallet.domain.common

import org.koin.mp.KoinPlatformTools

inline fun <reified T> inject(): T =
    KoinPlatformTools.defaultContext().get().get()
