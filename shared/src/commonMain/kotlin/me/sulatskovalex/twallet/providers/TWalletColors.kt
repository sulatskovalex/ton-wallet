package me.sulatskovalex.twallet.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val appColors: TWalletColors
    @Composable
    get() = LocalColors.current

data class TWalletColors(
    val background: Color,
    val surface: Color,
    val primary: Color,
    val secondary: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val disabledText: Color,
    val error: Color,
    val buttonText: Color = Color.White,
    val buttonOutlineText: Color = primaryText,
    val buttonBackground: Color = primary,
    val buttonOutlineBackground: Color = surface,
    val link: Color = Color(0xFF0055ff),
)

val LocalColors = staticCompositionLocalOf<TWalletColors> {
    error("No colors provided")
}
val darkPalette = TWalletColors(
    background = Color(0xFF121212),
    surface = Color(0xFF222222),
    primary = Color(0xFF00796B),
    secondary = Color(0xFF23282D),
    primaryText = Color(0xDEFFFFFF),
    secondaryText = Color(0x99FFFFFF),
    disabledText = Color(0x61FFFFFF),
    error = Color(0xFFFF5252)
)

val lightPalette = TWalletColors(
    background = Color(0xFFFAFAFA),
    surface = Color(0xFFFFFFFF),
    primary = Color(0xFF009688),
    secondary = Color(0xFF23282D),
    primaryText = Color(0xDE000000),
    secondaryText = Color(0x99000000),
    disabledText = Color(0x61000000),
    error = Color(0xFFFF5252)
)