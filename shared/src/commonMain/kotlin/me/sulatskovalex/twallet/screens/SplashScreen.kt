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
import androidx.compose.ui.unit.dp
import io.github.skeptick.libres.compose.painterResource
import me.sulatskovalex.twallet.AppScreens
import me.sulatskovalex.twallet.base.SafeAreaScreen
import me.sulatskovalex.twallet.common.Res
import me.sulatskovalex.twallet.providers.appColors
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.LaunchFlag

@Composable
fun SplashScreen(
    controller: RootController = LocalRootController.current,
) =
    SafeAreaScreen<SplashViewModel> { viewModel ->
        val size = remember { Animatable(0f) }
        LaunchedEffect(viewModel) {
            viewModel.onLaunch(
                {
                    controller.launch(
                        screen = AppScreens.Start.name,
                        launchFlag = LaunchFlag.SingleNewTask,
                    )
                },
                {
                    controller.launch(
                        screen = AppScreens.Home.name,
                        launchFlag = LaunchFlag.SingleNewTask,
                    )
                })
            size.animateTo(
                120f,
                tween(
                    durationMillis = 450,
                    easing = LinearEasing
                ),
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(appColors.background),
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(size.value.dp),
                painter = painterResource(Res.image.ic_ton),
                contentDescription = null,
            )
        }
    }

