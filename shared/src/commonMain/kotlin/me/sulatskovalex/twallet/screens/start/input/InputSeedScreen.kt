package me.sulatskovalex.twallet.screens.start.input

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.sulatskovalex.twallet.AppScreens
import me.sulatskovalex.twallet.base.IconButton
import me.sulatskovalex.twallet.base.LoadingButton
import me.sulatskovalex.twallet.base.OutlinedTextField
import me.sulatskovalex.twallet.base.SafeAreaScreen
import me.sulatskovalex.twallet.base.rippleClickable
import me.sulatskovalex.twallet.common.Res
import me.sulatskovalex.twallet.providers.appColors
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.LaunchFlag

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputSeedScreen(
    controller: RootController = LocalRootController.current
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
                        IconButton(
                            painter = rememberVectorPainter(Icons.Default.ArrowBack),
                            onClick = controller::popBackStack,
                        )
                    },
                    backgroundColor = appColors.surface,
                )
            },
            backgroundColor = appColors.background,
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(appColors.background),
            ) {
                val state = viewModel.state.value
                val scope = rememberCoroutineScope()
                val wordCoordinates =
                    remember { mutableStateOf(mutableMapOf<Int, LayoutCoordinates?>()) }
                val changedWordIndex = remember { mutableStateOf(-1) }
                val wordFocusRequesters = remember {
                    mutableStateOf((0 until 24).map { FocusRequester() })
                }
                val keyboardController = LocalSoftwareKeyboardController.current
                Column(Modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .pointerInput(state) {
                                awaitPointerEventScope {
                                    awaitPointerEvent(PointerEventPass.Initial)
                                    changedWordIndex.value = -1
                                }
                            },
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 78.dp),
                    )
                    {
                        items(
                            count = state.words.size,
                            key = { index -> index }
                        ) { index ->
                            EditableWordItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp, end = 8.dp, bottom = 4.dp)
                                    .onGloballyPositioned {
                                        wordCoordinates.value[index] = it
                                        changedWordIndex.value = changedWordIndex.value
                                    },
                                index = index,
                                word = state.words[index],
                                focusRequester = wordFocusRequesters.value[index],
                                nextFieldFocusRequester = wordFocusRequesters.value.getOrNull(index + 1),
                                enabled = !state.isLoading,
                                onValueChange = {
                                    changedWordIndex.value = index
                                    viewModel.onValueChanged(index, it)
                                },
                            )
                        }
                    }

                    LoadingButton(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        text = Res.string.next,
                        isLoading = state.isLoading,
                        onClick = {
                            keyboardController?.hide()
                            viewModel.onClick(
                                onGoToHome = {
                                    controller.launch(
                                        screen = AppScreens.Home.name,
                                        launchFlag = LaunchFlag.SingleNewTask,
                                    )
                                },
                            )
                        },
                    )
                }

                val wordIndex = changedWordIndex.value
                if (wordIndex != -1) {
                    val coordinates = wordCoordinates.value[wordIndex]
                    val suggestions = state.words[wordIndex].suggestions
                    if (suggestions != null && coordinates != null) {
                        DropdownMenu(
                            coordinates = coordinates,
                            suggestions = suggestions,
                            onSuggestionClick = { suggestion ->
                                viewModel.onValueChanged(wordIndex, TextFieldValue(suggestion))
                                scope.launch {
                                    delay(150)
                                    wordFocusRequesters.value
                                        .getOrNull(wordIndex + 1)
                                        ?.requestFocus()
                                }
                            }
                        )
                    }
                }
            }
        }
    }


@Composable
private fun DropdownMenu(
    coordinates: LayoutCoordinates,
    suggestions: List<String>,
    onSuggestionClick: (String) -> Unit,
) {
    val positionInParent = coordinates.positionInParent()
    val density = LocalDensity.current.density
    val y = (positionInParent.y + coordinates.size.height) / density
    val x = positionInParent.x / density
    Box(
        Modifier.fillMaxSize().focusable(false)
    ) {
        LazyColumn(
            Modifier
                .height(
                    if (suggestions.size > 5)
                        (32.dp * 5) + 16.dp
                    else
                        (32.dp * suggestions.size) + 16.dp
                )
                .offset(x.dp, y.dp)
                .background(appColors.surface, RoundedCornerShape(8.dp)),
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            items(suggestions.size) {
                val suggestion = suggestions[it]
                Text(
                    text = suggestion,
                    modifier = Modifier
                        .height(32.dp)
                        .padding(horizontal = 16.dp)
                        .rippleClickable { onSuggestionClick.invoke(suggestion) },
                    fontSize = 16.sp,
                    color = appColors.primaryText,
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditableWordItem(
    modifier: Modifier,
    index: Int,
    word: WordItem,
    focusRequester: FocusRequester,
    nextFieldFocusRequester: FocusRequester?,
    enabled: Boolean,
    onValueChange: (TextFieldValue) -> Unit,
) {
    val isFocused = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    Box(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier
                .height(42.dp)
                .fillMaxWidth()
                .background(appColors.surface, RoundedCornerShape(4.dp))
                .focusRequester(focusRequester)
                .onFocusChanged { isFocused.value = it.isFocused },
            value = word.fieldValue,
            enabled = enabled,
            isError = (!isFocused.value || word.forceError) && word.isError,
            keyboardOptions = KeyboardOptions(imeAction = if (index == 23) ImeAction.Done else ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    nextFieldFocusRequester?.requestFocus() ?: keyboardController?.hide()
                },
                onDone = {
                    keyboardController?.hide()
                }
            ),
            onValueChange = onValueChange,
            contentPadding = PaddingValues(
                start = if (index > 8) 32.dp else 22.dp,
                top = 8.dp,
                end = 16.dp,
                bottom = 8.dp
            ),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = appColors.primaryText,
                backgroundColor = appColors.surface,
                focusedBorderColor = appColors.primary,
                errorBorderColor = appColors.error,
                unfocusedBorderColor = appColors.disabledText,
            )
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterStart),
            text = "${index + 1}.",
            color = appColors.primaryText,
        )
    }
}
