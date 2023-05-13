package me.sulatskovalex.twallet.screens.home.dialogs.send

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import com.adeo.kviewmodel.KViewModel
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.sulatskovalex.twallet.base.update
import me.sulatskovalex.twallet.domain.repositories.WalletRepository

class SendViewModel(
    private val walletRepository: WalletRepository,
) : KViewModel() {

    val state = mutableStateOf(SendState())
    private val validateAddressJob = SupervisorJob()

    fun onAmountChanged(amount: TextFieldValue) {
        if (amount.text.isEmpty() || amount.text.toFloatOrNull() != null) {
            state.update { it.copy(amount = amount) }
        }
    }

    fun onAddressChanged(address: TextFieldValue) {
        state.update {
            it.copy(
                address = address,
                isAddressError = false
            )
        }
        validateAddressJob.cancelChildren()
        viewModelScope.launch(validateAddressJob) {
            val isAddressError =
                address.text.isNotEmpty() &&
                        walletRepository.validateAddress(address.text) != address.text
            delay(500)
            state.update {
                it.copy(
                    address = address,
                    isAddressError = isAddressError,
                )
            }
        }
    }

    override fun onCleared() {
        validateAddressJob.cancel()
        state.update { SendState() }
        super.onCleared()
    }

    fun onCommentChanged(comment: TextFieldValue) {
        state.update { it.copy(comment = comment) }
    }
}

data class SendState(
    val amount: TextFieldValue = TextFieldValue(""),
    val address: TextFieldValue = TextFieldValue(""),
    val isAddressError: Boolean = false,
    val comment: TextFieldValue = TextFieldValue(""),
) {
    val isDataValid: Boolean
        get() = amount.text.toFloatOrNull() != null && address.text.isNotEmpty() && !isAddressError
}
