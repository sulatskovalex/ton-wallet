package me.sulatskovalex.twallet.screens.home.home.assets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.sulatskovalex.twallet.base.OutlinedButton
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
                .background(appColors.background)
                .padding(16.dp),
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "ADDRESS",
                color = appColors.primaryText,
            )
            Row(Modifier.width(displaySize.widthDp.dp / 2).align(Alignment.CenterHorizontally)) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    text = "Receive",
                    onClick = onReceiveClick,
                )
                Spacer(Modifier.width(16.dp))
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    text = "Send",
                    onClick = onSendClick,
                )
            }
        }
    }
