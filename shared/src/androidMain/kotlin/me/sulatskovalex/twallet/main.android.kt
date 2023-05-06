package me.sulatskovalex.twallet

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.StartScreen

@Composable
fun MainView(activity: ComponentActivity) = App(
    configuration = OdysseyConfiguration(
        canvas = activity,
        startScreen = StartScreen.Custom(AppScreens.Splash.name),
    ),
    onFinish = activity::finish,
)
