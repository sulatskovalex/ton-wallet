package me.sulatskovalex.twallet

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import me.sulatskovalex.twallet.providers.DisplaySize
import me.sulatskovalex.twallet.providers.Platform
import me.sulatskovalex.twallet.providers.appColors
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.StartScreen
import ru.alexgladkov.odyssey.core.configuration.DisplayType

@Composable
fun MainView(activity: ComponentActivity) =
    App(
        configuration = {
            OdysseyConfiguration(
                canvas = activity,
                startScreen = StartScreen.Custom(AppScreens.Splash.name),
                backgroundColor = appColors.background,
                navigationBarColor = appColors.primary.copy(alpha = .5f).toArgb(),
                statusBarColor = appColors.primary.copy(alpha = .5f).toArgb(),
                displayType = DisplayType.FullScreen,
            )
        },
        displaySize = LocalConfiguration.current.let {
            DisplaySize(
                widthDp = it.screenWidthDp,
                heightDp = it.screenHeightDp,
            )
        },
        platform = Platform.Android,
        onFinish = activity::finish,
    )
