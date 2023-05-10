package me.sulatskovalex.twallet.screens.home.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.sulatskovalex.twallet.base.SafeAreaScreen
import me.sulatskovalex.twallet.providers.appColors

@Composable
fun SettingsScreen(
    onGotoSplash: () -> Unit,
) =
    SafeAreaScreen<SettingsViewModel> { viewModel ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .scrollable(rememberScrollState(), Orientation.Vertical),
        ) {
            Text(
                text = "assets",
                color = appColors.primaryText,
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            )
            Text(
                text = "assets",
                color = appColors.primaryText,
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            )
            Text(
                text = "Exit",
                color = appColors.primaryText,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { viewModel.onExitClick(onGotoSplash) })
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            )
        }
    }
