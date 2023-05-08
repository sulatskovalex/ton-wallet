package me.sulatskovalex.twallet.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.skeptick.libres.compose.painterResource
import me.sulatskovalex.twallet.base.Screen
import me.sulatskovalex.twallet.common.Res

@Composable
fun SplashScreen(
    onGoToStart: () -> Unit,
    onGoToHome: () -> Unit,
) {
    Screen<SplashViewModel> { viewModel ->
        LaunchedEffect(viewModel) {
            viewModel.onLaunch(onGoToStart, onGoToHome)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(120.dp),
                painter = painterResource(Res.image.ic_ton),
                contentDescription = null,
            )
        }

    }
}
