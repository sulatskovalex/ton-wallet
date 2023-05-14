package me.sulatskovalex.twallet.screens.home.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.sulatskovalex.twallet.base.SafeAreaScreen
import me.sulatskovalex.twallet.base.rippleClickable
import me.sulatskovalex.twallet.common.Res
import me.sulatskovalex.twallet.providers.appColors

@Composable
fun SettingsScreen(
    onExitClick: () -> Unit,
) =
    SafeAreaScreen<SettingsViewModel> { viewModel ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .scrollable(rememberScrollState(), Orientation.Vertical)
                .background(appColors.background),
        ) {
            Text(
                text = "assets",
                color = appColors.primaryText,
                modifier = Modifier.fillMaxWidth()
                    .rippleClickable { }
                    .padding(16.dp),
                fontSize = 16.sp,
            )
            Row(
                modifier = Modifier.fillMaxWidth()
                    .rippleClickable { }
                    .padding(horizontal = 16.dp),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth()
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(vertical = 16.dp),
                    text = "Testnet",
                    color = appColors.primaryText,
                    fontSize = 16.sp,
                )
                Switch(viewModel.isTestnetState.value, viewModel::switch)
            }
            Text(
                text = Res.string.disconnect_wallet,
                color = appColors.primaryText,
                modifier = Modifier
                    .fillMaxWidth()
                    .rippleClickable(onClick = onExitClick)
                    .padding(16.dp),
                fontSize = 16.sp,
            )
        }
    }
