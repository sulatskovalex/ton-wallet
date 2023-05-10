package me.sulatskovalex.twallet.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

@Immutable
data class DisplaySize(
    val widthDp: Int,
    val heightDp: Int,
)

val LocalDisplaySize = staticCompositionLocalOf<DisplaySize> { error("display size required") }

val displaySize: DisplaySize
    @Composable get() = LocalDisplaySize.current