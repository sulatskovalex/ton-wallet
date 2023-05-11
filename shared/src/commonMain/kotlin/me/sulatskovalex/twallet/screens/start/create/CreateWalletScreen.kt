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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.sulatskovalex.twallet.AppScreens
import me.sulatskovalex.twallet.base.AlertButton
import me.sulatskovalex.twallet.base.AlertDialog
import me.sulatskovalex.twallet.base.Button
import me.sulatskovalex.twallet.base.IconButton
import me.sulatskovalex.twallet.base.LoadingButton
import me.sulatskovalex.twallet.base.SafeAreaScreen
import me.sulatskovalex.twallet.common.Res
import me.sulatskovalex.twallet.providers.appColors
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.controllers.ModalController
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import ru.alexgladkov.odyssey.core.LaunchFlag
import kotlin.random.Random

@Composable
fun CreateWalletScreen(
    controller: RootController = LocalRootController.current,
    modalController: ModalController = controller.findModalController(),
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
                        IconButton(
                            painter = rememberVectorPainter(Icons.Default.ArrowBack),
                            contentDescription = Res.string.back,
                            onClick = controller::popBackStack
                        )
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

                val words = if (state.isLoadingWords)
                    state.randomWords
                else
                    state.words
                if (words.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .scrollable(rememberScrollState(), Orientation.Vertical)
                    ) {
                        (0 until 12).forEach { index ->
                            DoubleWordItem(
                                modifier = Modifier.padding(
                                    start = 24.dp,
                                    top = 4.dp,
                                    bottom = 4.dp,
                                    end = 24.dp
                                ).alpha(
                                    if (state.isLoadingWords)
                                        Random.Default.nextInt(from = 10, until = 100) / 100f
                                    else
                                        1f
                                ),
                                firstIndex = index,
                                firstWord = words[index],
                                secondIndex = index + 12,
                                secondWord = words[index + 12],
                            )
                        }
                    }
                    LoadingButton(
                        modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                        text = Res.string.next,
                        isLoading = state.isLoadingWords || state.isLoadingWallet,
                        onClick = {
                            modalController.showConfirmDialog {
                                viewModel.onConfirmOkClick {
                                    controller.launch(
                                        screen = AppScreens.Home.name,
                                        launchFlag = LaunchFlag.SingleNewTask,
                                    )
                                }
                            }
                        },
                    )
                }

                if (state.isError) {
                    Column {
                        Text(
                            text = "error",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = appColors.error,
                        )
                        Button(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = Res.string.retry,
                            onClick = {
                                viewModel.onReloadClick {
                                    controller.launch(
                                        screen = AppScreens.Home.name,
                                        launchFlag = LaunchFlag.SingleNewTask,
                                    )
                                }
                            },
                        )
                    }
                }
            }
        }
    }

@Composable
inline fun DoubleWordItem(
    modifier: Modifier,
    firstIndex: Int,
    firstWord: String,
    secondIndex: Int,
    secondWord: String
) =
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

@Composable
inline fun WordItem(modifier: Modifier, index: Int, word: String) =
    Text(
        modifier = modifier,
        text = "${index + 1}. $word",
        fontSize = 16.5.sp,
        fontWeight = FontWeight.SemiBold,
        color = appColors.primaryText,
    )

private fun ModalController.showConfirmDialog(onOkClick: () -> Unit) =
    present(AlertConfiguration(cornerRadius = 4)) { key ->
        AlertDialog(
            Res.string.confirm_words_saved_title,
            appColors.primaryText,
            Res.string.confirm_words_saved_message,
            appColors.secondaryText,
            AlertButton(Res.string.ok, appColors.secondaryText) {
                popBackStack(key)
                onOkClick.invoke()
            },
            AlertButton(Res.string.cancel, appColors.secondaryText) {
                popBackStack(key)
            }
        )
    }