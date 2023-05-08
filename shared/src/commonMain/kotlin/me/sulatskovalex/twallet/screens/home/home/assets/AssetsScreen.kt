package me.sulatskovalex.twallet.screens.home.home.assets

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import me.sulatskovalex.twallet.base.Screen

@Composable
fun AssetsScreen() {
    Screen<AssetsViewModel> { viewModel ->
        Column {
            Text("assets")
        }
    }
}
