package me.sulatskovalex.twallet.screens.home.settings

import com.adeo.kviewmodel.KViewModel
import kotlinx.coroutines.launch
import me.sulatskovalex.twallet.domain.repositories.WalletRepository

class SettingsViewModel(
    private val repository: WalletRepository,
) : KViewModel() {
    fun onExitClick(onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.deleteCurrentWallet()
            onComplete.invoke()
        }
    }
}
