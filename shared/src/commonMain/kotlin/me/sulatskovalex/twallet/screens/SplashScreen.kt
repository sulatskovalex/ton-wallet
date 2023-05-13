package me.sulatskovalex.twallet.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import me.sulatskovalex.twallet.AppScreens
import me.sulatskovalex.twallet.base.SafeAreaScreen
import me.sulatskovalex.twallet.base.tonIcon
import me.sulatskovalex.twallet.providers.appColors
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.LaunchFlag

@Composable
fun SplashScreen(
    controller: RootController = LocalRootController.current,
) =
    SafeAreaScreen<SplashViewModel> { viewModel ->
        val alpha = remember { Animatable(0f) }
        LaunchedEffect(viewModel) {
            viewModel.onLaunch(
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
                })
            alpha.animateTo(
                1f,
                tween(
                    durationMillis = 750,
                    easing = LinearEasing
                )
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(appColors.background)
                .alpha(alpha.value),
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(120.dp),
                painter = rememberVectorPainter(tonIcon),
                contentDescription = null,
            )
        }
    }

