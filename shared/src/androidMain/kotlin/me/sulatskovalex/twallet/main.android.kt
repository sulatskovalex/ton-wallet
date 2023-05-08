package me.sulatskovalex.twallet

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import me.sulatskovalex.twallet.providers.DisplaySize
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.StartScreen

@Composable
fun MainView(activity: ComponentActivity) =
    App(
        configuration = OdysseyConfiguration(
            canvas = activity,
            startScreen = StartScreen.Custom(AppScreens.Splash.name),
        ),
        displaySize = LocalConfiguration.current.let {
            DisplaySize(it.screenWidthDp, it.screenHeightDp)
        },
        onFinish = activity::finish,
    )
