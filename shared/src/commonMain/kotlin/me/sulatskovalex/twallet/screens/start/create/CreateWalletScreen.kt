package me.sulatskovalex.twallet.screens.start.create

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.sulatskovalex.twallet.base.SafeAreaScreen
import me.sulatskovalex.twallet.common.Res
import me.sulatskovalex.twallet.providers.appColors

@Composable
fun CreateWalletScreen(
    onGoToHome: () -> Unit,
    onBackClick: () -> Unit,
) =
    SafeAreaScreen<CreateWalletViewModel> { viewModel ->
        LaunchedEffect(viewModel) {
            viewModel.onLaunch()
        }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = Res.string.new_wallet,
                            color = appColors.primaryText,
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    navigationIcon = {
                        IconButton(onBackClick) {
                            Icon(
                                painter = rememberVectorPainter(Icons.Default.ArrowBack),
                                contentDescription = "Back",
                                tint = appColors.primaryText,
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
                val state = viewModel.state.value
                if (state.isError) {
                    Column {
                        Text(
                            text = "error",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = appColors.error,
                        )
                        Button(
                            onClick = { viewModel.onReloadClick(onGoToHome) },
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = appColors.buttonBackground
                            )
                        ) {
                            Text(
                                text = Res.string.retry,
                                color = appColors.buttonText,
                            )
                        }
                    }
                } else if (state.words.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .scrollable(rememberScrollState(), Orientation.Vertical),
                    ) {
                        (0 until 12).forEach { index ->
                            DoubleWordItem(
                                modifier = Modifier.padding(
                                    start = 24.dp,
                                    top = 4.dp,
                                    bottom = 4.dp,
                                    end = 24.dp
                                ),
                                firstIndex = index,
                                firstWord = state.words[index],
                                secondIndex = index + 12,
                                secondWord = state.words[index + 12],
                            )
                        }
                    }
                    if (!state.isLoading) {
                        Button(
                            onClick = { viewModel.onNextClick(onGoToHome) },
                            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = appColors.buttonBackground,
                            )
                        ) {
                            Text(
                                text = Res.string.next,
                                color = appColors.buttonText,
                            )
                        }
                    }
                }
            }
        }
    }

@Composable
fun DoubleWordItem(
    modifier: Modifier,
    firstIndex: Int,
    firstWord: String,
    secondIndex: Int,
    secondWord: String
) {
    Row(modifier = modifier) {
        WordItem(
            modifier = Modifier.weight(1f),
            index = firstIndex,
            word = firstWord,
        )
        Spacer(modifier = Modifier.width(16.dp))
        WordItem(
            modifier = Modifier.weight(1f),
            index = secondIndex,
            word = secondWord,
        )
    }
}

@Composable
fun WordItem(modifier: Modifier, index: Int, word: String) {
    Text(
        modifier = modifier,
        text = "${index + 1}. $word",
        fontSize = 16.5.sp,
        fontWeight = FontWeight.SemiBold,
        color = appColors.primaryText,
    )
}
