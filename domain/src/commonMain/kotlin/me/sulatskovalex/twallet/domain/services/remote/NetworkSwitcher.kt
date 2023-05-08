package me.sulatskovalex.twallet.domain.services.remote

expect class NetworkSwitcher {

    var isTestnet: Boolean

    fun watch(listener: Listener)

}

interface Listener {
    fun onNetworkChanged(isTestnet: Boolean)
}
