package me.sulatskovalex.twallet.screens.home.assets

import androidx.compose.runtime.mutableStateOf
import com.adeo.kviewmodel.KViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.sulatskovalex.twallet.base.update
import me.sulatskovalex.twallet.domain.models.WalletInfo
import me.sulatskovalex.twallet.domain.repositories.WalletRepository

class AssetsViewModel(
    private val walletRepository: WalletRepository,
) : KViewModel() {

    val walletInfo = mutableStateOf(WalletInfo())

    fun onLaunch() {
        walletRepository.walletInfo
            .onEach { newInfo ->
                walletInfo.update { newInfo }
            }.launchIn(viewModelScope)
    }

}