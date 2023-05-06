package me.sulatskovalex.twallet

import androidx.compose.ui.window.ComposeUIViewController
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.StartScreen

fun MainViewController() =
    ComposeUIViewController {
        App(
            configuration = OdysseyConfiguration(
                startScreen = StartScreen.Custom(AppScreens.Splash.name)
            ),
            onFinish = {},
        )
    }