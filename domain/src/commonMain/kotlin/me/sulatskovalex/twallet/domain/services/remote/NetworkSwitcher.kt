package me.sulatskovalex.twallet.domain.services.remote

expect class NetworkSwitcher {

    var isTestnet: Boolean

    fun watch(listener: NetworkSwitcherListener)

}

interface NetworkSwitcherListener {
    fun onNetworkChanged(isTestnet: Boolean)
}
