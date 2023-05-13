package me.sulatskovalex.twallet.screens.home.dialogs.send

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.skeptick.libres.compose.painterResource
import me.sulatskovalex.twallet.base.BottomSheetHeader
import me.sulatskovalex.twallet.base.Button
import me.sulatskovalex.twallet.base.IconButton
import me.sulatskovalex.twallet.base.OutlinedTextField
import me.sulatskovalex.twallet.base.Screen
import me.sulatskovalex.twallet.base.tonIcon
import me.sulatskovalex.twallet.common.Res
import me.sulatskovalex.twallet.providers.appColors
import me.sulatskovalex.twallet.providers.displaySize
import ru.alexgladkov.odyssey.compose.controllers.ModalController
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalSheetConfiguration

@OptIn(ExperimentalComposeUiApi::class)
inline fun ModalController.showSendDialog(
    noinline onScanClick: (onAddressScanned: (String) -> Unit) -> Unit,
    noinline onContinueClick: (address: String, amount: String, comment: String, onComplete: () -> Unit) -> Unit,
) =
    present(
        ModalSheetConfiguration(cornerRadius = 8)
    ) { key ->
        Screen<SendViewModel> { viewModel ->
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(displaySize.heightDp.dp / 1.1f)
                    .background(appColors.surface)
            ) {
                BottomSheetHeader { popBackStack(key) }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp)
                ) {
                    val isFocusRequested = remember { mutableStateOf(false) }
                    val amountFocusRequester = remember { FocusRequester() }
                    val addressFocusRequester = remember { FocusRequester() }
                    val commentFocusRequester = remember { FocusRequester() }
                    val keyboard = LocalSoftwareKeyboardController.current
                    val state = viewModel.state
                    Spacer(Modifier.height(16.dp))
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .focusRequester(amountFocusRequester)
                            .onGloballyPositioned {
                                if (!isFocusRequested.value) {
                                    isFocusRequested.value = true
                                    amountFocusRequester.requestFocus()
                                }
                            },
                        value = state.value.amount,
                        onValueChange = viewModel::onAmountChanged,
                        trailingIcon = {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = rememberVectorPainter(tonIcon),
                                contentDescription = Res.string.ton,
                            )
                        },
                        textStyle = TextStyle(
                            color = appColors.primaryText,
                            fontSize = 42.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.End,
                        ),
                        placeholder = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End,
                                text = "0",
                                fontSize = 42.sp,
                                color = appColors.disabledText,
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = appColors.primaryText,
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                addressFocusRequester.requestFocus()
                            },
                        ),
                        singleLine = true,
                    )
                    Box(
                        Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = state.value.address,
                            onValueChange = viewModel::onAddressChanged,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterStart)
                                .focusRequester(addressFocusRequester),
                            textStyle = TextStyle(appColors.primaryText, fontSize = 18.sp),
                            placeholder = {
                                Text(
                                    text = Res.string.wallet_address_or_domain,
                                    textAlign = TextAlign.Start,
                                    fontSize = 16.sp,
                                    color = appColors.disabledText,
                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                capitalization = KeyboardCapitalization.None,
                                autoCorrect = false,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done,
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    commentFocusRequester.requestFocus()
                                },
                            ),
                            isError = state.value.isAddressError,
                            minLines = 2,
                            maxLines = 3,
                            contentPadding = PaddingValues(8.dp, 8.dp, 56.dp, 8.dp),
                        )
                        IconButton(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            painter = painterResource(Res.image.ic_scan_qr),
                            onClick = {
                                amountFocusRequester.freeFocus()
                                keyboard?.hide()
                                onScanClick.invoke { scannedAddress ->
                                    viewModel.onAddressChanged(TextFieldValue(scannedAddress))
                                }
                            }
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = state.value.comment,
                        onValueChange = viewModel::onCommentChanged,
                        modifier = Modifier.fillMaxWidth()
                            .focusRequester(commentFocusRequester),
                        textStyle = TextStyle(appColors.primaryText, fontSize = 18.sp),
                        placeholder = {
                            Text(
                                text = Res.string.comment_memo,
                                textAlign = TextAlign.Start,
                                fontSize = 16.sp,
                                color = appColors.disabledText,
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done,
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboard?.hide() },
                        ),
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        text = Res.string.continue_btn,
                        isEnabled = state.value.isDataValid,
                        onClick = {
                            keyboard?.hide()
                            onContinueClick.invoke(
                                state.value.address.text,
                                state.value.amount.text,
                                state.value.comment.text
                            ) {
                                popBackStack(key)
                            }
                        }
                    )
                }
            }
        }
    }
