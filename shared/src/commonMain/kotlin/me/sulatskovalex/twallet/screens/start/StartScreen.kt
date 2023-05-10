package me.sulatskovalex.twallet.screens.start

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.skeptick.libres.compose.painterResource
import me.sulatskovalex.twallet.base.SafeAreaScreen
import me.sulatskovalex.twallet.common.Res
import me.sulatskovalex.twallet.providers.appColors

@Composable
fun StartScreen(
    onCreateWalletClick: () -> Unit,
    onInputSeedClick: () -> Unit,
) =
    SafeAreaScreen<StartViewModel> { viewModel ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(appColors.background),
        ) {
            Image(
                modifier = Modifier.size(120.dp).align(Alignment.Center),
                painter = painterResource(Res.image.ic_ton),
                contentDescription = null,
            )
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onCreateWalletClick,
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = appColors.buttonOutlineBackground,
                    ),
                    border = BorderStroke(1.dp, appColors.surface)

                ) {
                    Text(
                        text = Res.string.create_wallet,
                        color = appColors.buttonOutlineText,
                    )
                }
                Spacer(Modifier.height(8.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onInputSeedClick,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = appColors.buttonBackground,
                    )
                ) {
                    Text(
                        text = Res.string.input_seed,
                        color = appColors.buttonText,
                    )
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
