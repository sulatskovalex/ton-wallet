package me.sulatskovalex.twallet.screens.home.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.sulatskovalex.twallet.base.Screen

@Composable
fun SettingsScreen(
    onGotoSplash: () -> Unit,
) {
    Screen<SettingsViewModel> { viewModel ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .scrollable(rememberScrollState(), Orientation.Vertical),
        ) {
            Text("assets")
            Text("assets")
            Text(
                text = "Exit",
                modifier = Modifier.clickable(onClick = { viewModel.onExitClick(onGotoSplash) }),
            )
        }
    }
}