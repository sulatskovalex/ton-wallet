package me.sulatskovalex.twallet.screens.start.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import me.sulatskovalex.twallet.base.SafeAreaScreen
import me.sulatskovalex.twallet.common.Res
import me.sulatskovalex.twallet.providers.appColors

@Composable
fun InputSeedScreen(
    onGoToHome: () -> Unit,
    onBackClick: () -> Unit,
) =
    SafeAreaScreen<InputSeedViewModel> { viewModel ->
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = Res.string.import_wallet,
                            color = appColors.primaryText,
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    navigationIcon = {
                        IconButton(onBackClick) {
                            Icon(
                                painter = rememberVectorPainter(Icons.Default.ArrowBack),
                                contentDescription = ""
                            )
                        }
                    },
                    backgroundColor = appColors.surface,
                )
            },
            backgroundColor = appColors.background,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp)
                    .background(appColors.background),
            ) {
                Button(
                    onClick = onGoToHome,
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = appColors.buttonBackground,
                    )
                ) {
                    Text(
                        text = Res.string.next,
                        color = appColors.buttonText
                    )
                }
            }
        }
    }