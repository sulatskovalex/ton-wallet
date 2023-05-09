package me.sulatskovalex.twallet.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

enum class Platform {
    Android,
    iOs;

    val iOS: Boolean
        get() = this == iOs

    val android: Boolean
        get() = this == Android
}

val LocalPlatform = staticCompositionLocalOf<Platform> { error("platform required") }

val platform: Platform
    @Composable get() = LocalPlatform.current
