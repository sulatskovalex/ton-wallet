package me.sulatskovalex.twallet

import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.cinterop.useContents
import me.sulatskovalex.twallet.providers.DisplaySize
import platform.UIKit.UIScreen
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.StartScreen
import kotlin.math.roundToInt

fun MainViewController() =
    ComposeUIViewController {
        App(
            configuration = OdysseyConfiguration(
                startScreen = StartScreen.Custom(AppScreens.Splash.name)
            ),
            displaySize = UIScreen.mainScreen.bounds.useContents {
                DisplaySize(size.width.roundToInt(), size.height.roundToInt())
            },
            onFinish = {},
        )
    }