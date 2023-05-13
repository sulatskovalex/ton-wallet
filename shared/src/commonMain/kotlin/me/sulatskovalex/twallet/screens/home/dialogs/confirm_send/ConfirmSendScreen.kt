package me.sulatskovalex.twallet.screens.home.dialogs.confirm_send

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.sulatskovalex.twallet.base.BottomSheetHeader
import me.sulatskovalex.twallet.base.LoadingButton
import me.sulatskovalex.twallet.common.Res
import me.sulatskovalex.twallet.providers.appColors
import me.sulatskovalex.twallet.providers.displaySize
import ru.alexgladkov.odyssey.compose.controllers.ModalController
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalSheetConfiguration

inline fun ModalController.showConfirmSendScreen(
    address: String,
    amount: String,
    comment: String,
    noinline onComplete: () -> Unit,
) =
    present(ModalSheetConfiguration()) { key ->
        Column(
            Modifier
                .fillMaxWidth()
                .height(displaySize.heightDp.dp / 1.2f)
                .background(appColors.surface)
        ) {
            BottomSheetHeader { popBackStack(key) }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 16.dp)
            ) {
                val scope = rememberCoroutineScope()
                val isLoading = remember { mutableStateOf(false) }
                Text(text = address)
                Text(text = amount)
                Text(text = comment)
                LoadingButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = Res.string.send_btn,
                    isLoading = isLoading.value,
                    onClick = {
                        isLoading.value = true
                        scope.launch {
                            delay(1000)
                            isLoading.value = true
                            delay(500)
                            popBackStack(key)
                            delay(350)
                            onComplete.invoke()
                        }
                    },
                )
            }
        }
    }