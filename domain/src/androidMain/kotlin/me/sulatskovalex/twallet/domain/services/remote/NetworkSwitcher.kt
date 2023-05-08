package me.sulatskovalex.twallet.domain.services.remote

import android.content.Context

actual class NetworkSwitcher(context: Context) {
    private val prefs = context.getSharedPreferences(NETWORK_SWITCHER_PREFS, Context.MODE_PRIVATE)
    private val listeners = mutableSetOf<Listener>()

    actual var isTestnet: Boolean
        get() = prefs.getBoolean(IS_TESTNET_KEY, false)
        set(value) {
            prefs.edit().putBoolean(IS_TESTNET_KEY, value).apply()
            notify(value)
        }

    actual fun watch(listener: Listener) {
        listeners.add(listener)
    }

    private fun notify(value: Boolean) {
        listeners.forEach { it.onNetworkChanged(value) }
    }

    companion object {
        private const val NETWORK_SWITCHER_PREFS = "NetworkSwitcher.prefs"
        private const val IS_TESTNET_KEY = "NetworkSwitcher.is_testnet_key"
    }
}
