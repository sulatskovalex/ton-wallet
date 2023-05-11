package me.sulatskovalex.twallet.screens.home.home.assets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.sulatskovalex.twallet.base.SafeAreaScreen
import me.sulatskovalex.twallet.providers.appColors
import me.sulatskovalex.twallet.providers.displaySize

@Composable
fun AssetsScreen(
    onSendClick: () -> Unit,
    onReceiveClick: () -> Unit,
) =
    SafeAreaScreen<AssetsViewModel> { viewModel ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(appColors.background),
        ) {
            Card(
                modifier = Modifier
                    .width((displaySize.widthDp / 1.2f).dp)
                    .align(Alignment.CenterHorizontally),
                backgroundColor = appColors.surface,
            ) {
                Column(Modifier.fillMaxWidth().padding(16.dp)) {
                    Text(text = "ADDRESS", color = appColors.primaryText)
                    Row(Modifier.align(Alignment.CenterHorizontally)) {
                        OutlinedButton(onReceiveClick) {
                            Text(text = "Receive")
                        }
                        Spacer(Modifier.width(16.dp))
                        OutlinedButton(onSendClick) {
                            Text(text = "Send")
                        }
                    }
                }
            }
        }
    }
