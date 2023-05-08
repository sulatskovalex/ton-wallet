package me.sulatskovalex.twallet.screens.start.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import me.sulatskovalex.twallet.base.Screen
import me.sulatskovalex.twallet.common.Res

@Composable
fun CreateWalletScreen(
    onGoToHome: () -> Unit,
    onBackClick: () -> Unit,
) {
    Screen<CreateWalletViewModel> { viewModel ->
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(Res.string.new_wallet)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    navigationIcon = {
                        IconButton(onBackClick) {
                            Icon(
                                painter = rememberVectorPainter(Icons.Default.ArrowBack),
                                contentDescription = "Back"
                            )
                        }
                    },
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp)
                    .background(MaterialTheme.colors.background),
            ) {
                Button(
                    onClick = onGoToHome,
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                ) {
                    Text(Res.string.create_wallet)
                }
            }
        }
    }
}