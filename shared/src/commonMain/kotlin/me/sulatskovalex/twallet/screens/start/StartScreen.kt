package me.sulatskovalex.twallet.screens.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import me.sulatskovalex.twallet.AppScreens
import me.sulatskovalex.twallet.base.Button
import me.sulatskovalex.twallet.base.OutlinedButton
import me.sulatskovalex.twallet.base.SafeAreaScreen
import me.sulatskovalex.twallet.base.tonIcon
import me.sulatskovalex.twallet.common.Res
import me.sulatskovalex.twallet.providers.appColors
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun StartScreen(
    controller: RootController = LocalRootController.current
) =
    SafeAreaScreen<StartViewModel> { viewModel ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(appColors.background),
        ) {
            Image(
                modifier = Modifier.size(120.dp).align(Alignment.Center),
                painter = rememberVectorPainter(tonIcon),
                contentDescription = null,
            )
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = Res.string.create_wallet,
                    onClick = { controller.launch(screen = AppScreens.CreateWallet.name) },
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    text = Res.string.input_seed,
                    onClick = { controller.launch(screen = AppScreens.InputSeed.name) },
                )
                Spacer(Modifier.height(16.dp))
            }
        }
    }
