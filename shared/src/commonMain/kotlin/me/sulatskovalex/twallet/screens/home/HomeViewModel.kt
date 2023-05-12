package me.sulatskovalex.twallet.screens.home

import com.adeo.kviewmodel.KViewModel
import kotlinx.coroutines.launch
import me.sulatskovalex.twallet.domain.repositories.WalletRepository

class HomeViewModel(
    private val repository: WalletRepository,
) : KViewModel() {

    fun onExitClick(onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.deleteCurrentWallet()
            onComplete.invoke()
        }
    }

    fun validateAddress(scanned: String) =
        repository.validateAddress(scanned)
}