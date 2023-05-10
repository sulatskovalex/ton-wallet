package me.sulatskovalex.twallet.screens.home.home.assets

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import me.sulatskovalex.twallet.base.SafeAreaScreen

@Composable
fun AssetsScreen() =
    SafeAreaScreen<AssetsViewModel> { viewModel ->
        Column {
            Text("assets")
        }
    }
