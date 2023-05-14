package me.sulatskovalex.twallet.screens.home.settings

import androidx.compose.runtime.mutableStateOf
import com.adeo.kviewmodel.KViewModel
import me.sulatskovalex.twallet.domain.services.remote.NetworkSwitcher

class SettingsViewModel(
    private val networkSwitcher: NetworkSwitcher,
) : KViewModel() {
    val isTestnetState = mutableStateOf(networkSwitcher.isTestnet)

    fun switch(isTestnet: Boolean) {
        networkSwitcher.isTestnet = isTestnet
        isTestnetState.value = isTestnet
    }
}
