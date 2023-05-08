package me.sulatskovalex.twallet.domain.services.remote

import platform.Foundation.NSUserDefaults

actual class NetworkSwitcher() {
    private val defaults = NSUserDefaults.standardUserDefaults()
    private val listeners = mutableSetOf<Listener>()

    actual var isTestnet: Boolean
        get() = defaults.boolForKey(IS_TESTNET_KEY)
        set(value) {
            defaults.setBool(value, IS_TESTNET_KEY)
            notify(value)
        }

    actual fun watch(listener: Listener) {
        listeners.add(listener)
    }

    private fun notify(value: Boolean) {
        listeners.forEach { it.onNetworkChanged(value) }
    }

    companion object {
        private const val IS_TESTNET_KEY = "NetworkSwitcher.is_testnet_key"
    }
}
