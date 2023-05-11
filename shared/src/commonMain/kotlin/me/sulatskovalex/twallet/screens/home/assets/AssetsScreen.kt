package me.sulatskovalex.twallet.screens.home.assets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.sulatskovalex.twallet.base.OutlinedButton
import me.sulatskovalex.twallet.base.SafeAreaScreen
import me.sulatskovalex.twallet.providers.appColors

@Composable
fun AssetsScreen(
    onSendClick: () -> Unit,
    onReceiveClick: (String) -> Unit,
) =
    SafeAreaScreen<AssetsViewModel> { viewModel ->
        LaunchedEffect(viewModel) {
            viewModel.onLaunch()
        }
        val walletInfo = remember { viewModel.walletInfo }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(appColors.background)
                .padding(16.dp),
        ) {
            Spacer(Modifier.height(24.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = walletInfo.value.amount,
                color = appColors.primaryText,
                fontSize = 32.sp,
            )
            Spacer(Modifier.height(16.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = walletInfo.value.shortAddress,
                color = appColors.primaryText,
                fontSize = 16.sp,
            )
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    text = "Receive",
                    onClick = { onReceiveClick.invoke(walletInfo.value.address) },
                )
                Spacer(Modifier.width(32.dp))
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    text = "Send",
                    onClick = onSendClick,
                )
            }
        }
    }
