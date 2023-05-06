package me.sulatskovalex.twallet.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.skeptick.libres.compose.painterResource
import kotlinx.coroutines.delay
import me.sulatskovalex.twallet.common.Res

@Composable
fun SplashScreen(
    onGoToMain: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LaunchedEffect(null) {
            delay(1000)
            onGoToMain.invoke()
        }
        Image(
            modifier = Modifier.align(Alignment.Center).size(80.dp),
            painter = painterResource(Res.image.ic_ton),
            contentDescription = null,
        )
    }
}