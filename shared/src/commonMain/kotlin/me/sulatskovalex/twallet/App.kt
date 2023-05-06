package me.sulatskovalex.twallet

import androidx.compose.runtime.Composable
import me.sulatskovalex.twallet.screens.SplashScreen
import me.sulatskovalex.twallet.screens.start.StartScreen
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ru.alexgladkov.odyssey.core.LaunchFlag

@Composable
fun App(
    configuration: OdysseyConfiguration,
    onFinish: () -> Unit
) {
    setNavigationContent(
        configuration = configuration,
        onApplicationFinish = onFinish,
    ) {
        screen(AppScreens.Splash.name) {
            val current = LocalRootController.current
            SplashScreen(
                onGoToMain = {
                    current.launch(
                        screen = AppScreens.Start.name,
                        launchFlag = LaunchFlag.SingleNewTask,
                    )
                }
            )
        }

        screen(AppScreens.Start.name) {
            StartScreen(
                onCreateWalletClick = {},
                onInputSeedClick = {}
            )
        }
    }
}
