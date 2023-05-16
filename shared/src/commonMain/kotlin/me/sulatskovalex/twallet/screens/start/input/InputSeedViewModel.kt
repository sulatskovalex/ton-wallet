package me.sulatskovalex.twallet.screens.start.input

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.adeo.kviewmodel.KViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.sulatskovalex.twallet.base.update
import me.sulatskovalex.twallet.domain.repositories.WalletRepository

class InputSeedViewModel(
    private val walletRepository: WalletRepository,
) : KViewModel() {

    private val allWords = walletRepository.mnemonicWords()

    val state = mutableStateOf(InputSeedState())

    fun onClick(onGoToHome: () -> Unit) {
        var hasErrors = false
        val words = state.value.words.toMutableList()
        words.forEachIndexed { index, word ->
            if (word.isError || word.fieldValue.text.isEmpty() || !allWords.contains(word.fieldValue.text)) {
                hasErrors = true
                words[index] = word.copy(isError = true, forceError = true)
            }
        }
        if (hasErrors) {
            state.update { it.copy(words = words) }
            return
        }

        viewModelScope.launch(Dispatchers.Default) {
            state.update { it.copy(isLoading = true) }
            try {
                walletRepository.createWallet(words.map { it.fieldValue.text })
                launch(Dispatchers.Main) {
                    onGoToHome.invoke()
                }
            } catch (t: Throwable) {
                t.printStackTrace()
            }
            state.update { it.copy(isLoading = false) }
        }
    }

    fun onValueChanged(index: Int, value: TextFieldValue) {
        val words = state.value.words.toMutableList()
        val wordItem = words[index]
        words[index] = wordItem.copy(
            fieldValue = value.copy(selection = TextRange(value.text.length)),
            suggestions = if (value.text.isEmpty()) {
                null
            } else {
                allWords.filter { word ->
                    word.startsWith(value.text)
                }.takeIf { filtered ->
                    filtered.isNotEmpty() && (filtered.size > 1 || filtered.first() != value.text)
                }
            },
            isError = value.text.isNotEmpty() && !allWords.contains(value.text),
        )
        state.update { it.copy(words = words, isLoading = false) }
    }
}

data class InputSeedState(
    val words: List<WordItem> = (0 until 24).map {
        WordItem(
            TextFieldValue(
                text = seed[it], selection = TextRange(
                    seed[it].length
                )
            )
        )
    },
    val isLoading: Boolean = false,
)

val seed =
    ("heart alley choose radio bone initial peasant spike found festival battle super remind network odor ozone eye help side seek glass biology cup stomach").split(
        " "
    )

@Immutable
data class WordItem(
    val fieldValue: TextFieldValue = TextFieldValue(""),
    val suggestions: List<String>? = null,
    val isError: Boolean = false,
    val forceError: Boolean = false,
)