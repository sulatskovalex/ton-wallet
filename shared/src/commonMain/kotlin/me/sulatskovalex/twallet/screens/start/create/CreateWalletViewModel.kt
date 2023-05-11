package me.sulatskovalex.twallet.screens.start.create

import androidx.compose.runtime.mutableStateOf
import com.adeo.kviewmodel.KViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.sulatskovalex.twallet.base.update
import me.sulatskovalex.twallet.domain.repositories.WalletRepository

class CreateWalletViewModel(
    private val walletRepository: WalletRepository,
) : KViewModel() {

    val state = mutableStateOf(State())

    fun onLaunch() {
        val randomWordsJob = viewModelScope.launch {
            repeat(Int.MAX_VALUE) {
                state.update { it.randomWords(walletRepository.randomWords()) }
                delay(350)
            }
        }
        viewModelScope.launch {
            state.update { it.loadingWords() }
            try {
                val words = walletRepository.generateWords()
                randomWordsJob.cancel()
                delay(350)
                state.update { it.words(words) }
            } catch (t: Throwable) {
                randomWordsJob.cancel()
                state.update { it.error() }
            }
        }
    }

    fun onReloadClick(onGoToHome: () -> Unit) {
        if (state.value.words.isEmpty()) {
            onLaunch()
        } else {
            onConfirmOkClick(onGoToHome)
        }
    }


    fun onConfirmOkClick(onGoToHome: () -> Unit) {
        viewModelScope.launch {
            try {
                state.update { it.loadingWallet() }
                walletRepository.createWallet(state.value.words)
                onGoToHome.invoke()
            } catch (t: Throwable) {
                state.update { it.error() }
            }
        }
    }
}