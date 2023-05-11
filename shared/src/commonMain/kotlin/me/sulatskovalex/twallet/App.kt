package me.sulatskovalex.twallet

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import me.sulatskovalex.twallet.providers.DisplaySize
import me.sulatskovalex.twallet.providers.LocalColors
import me.sulatskovalex.twallet.providers.LocalDisplaySize
import me.sulatskovalex.twallet.providers.LocalPlatform
import me.sulatskovalex.twallet.providers.Platform
import me.sulatskovalex.twallet.providers.darkPalette
import me.sulatskovalex.twallet.providers.lightPalette
import me.sulatskovalex.twallet.screens.SplashScreen
import me.sulatskovalex.twallet.screens.home.HomeScreen
import me.sulatskovalex.twallet.screens.start.StartScreen
import me.sulatskovalex.twallet.screens.start.create.CreateWalletScreen
import me.sulatskovalex.twallet.screens.start.input.InputSeedScreen
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ru.alexgladkov.odyssey.core.LaunchFlag

@Composable
fun App(
    configuration: @Composable () -> OdysseyConfiguration,
    displaySize: DisplaySize,
    platform: Platform,
    onFinish: () -> Unit
) {
    CompositionLocalProvider(
        LocalDisplaySize provides displaySize,
        LocalPlatform provides platform,
        LocalColors provides if (isSystemInDarkTheme()) darkPalette else lightPalette
    ) {
        setNavigationContent(
            configuration = configuration.invoke(),
            onApplicationFinish = onFinish,
        ) {
            screen(AppScreens.Splash.name) {
                val controller = LocalRootController.current
                SplashScreen(
                    onGoToStart = {
                        controller.launch(
                            screen = AppScreens.Start.name,
                            launchFlag = LaunchFlag.SingleNewTask,
                        )
                    },
                    onGoToHome = {
                        controller.launch(
                            screen = AppScreens.Home.name,
                            launchFlag = LaunchFlag.SingleNewTask,
                        )
                    },
                )
            }

            screen(AppScreens.Start.name) {
                val controller = LocalRootController.current
                StartScreen(
                    onCreateWalletClick = {
                        controller.launch(screen = AppScreens.CreateWallet.name)
                    },
                    onInputSeedClick = {
                        controller.launch(screen = AppScreens.InputSeed.name)
                    }
                )
            }
            screen(AppScreens.CreateWallet.name) {
                val controller = LocalRootController.current
                CreateWalletScreen(
                    onGoToHome = {
                        controller.launch(
                            screen = AppScreens.Home.name,
                            launchFlag = LaunchFlag.SingleNewTask,
                        )
                    },
                    onBackClick = {
                        controller.popBackStack()
                    },
                )
            }
            screen(AppScreens.InputSeed.name) {
                val controller = LocalRootController.current
                InputSeedScreen(
                    onGoToHome = {
                        controller.launch(
                            screen = AppScreens.Home.name,
                            launchFlag = LaunchFlag.SingleNewTask,
                        )
                    },
                    onBackClick = {
                        controller.popBackStack()
                    },
                )
            }
            screen(AppScreens.Home.name) {
                HomeScreen()
            }
        }
    }
}
