package me.sulatskovalex.twallet.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.sulatskovalex.twallet.domain.models.WalletInfo
import me.sulatskovalex.twallet.domain.services.remote.NetworkSwitcher
import me.sulatskovalex.twallet.domain.services.remote.NetworkSwitcherListener
import org.ton.api.liteclient.config.LiteClientConfigGlobal
import org.ton.block.Account
import org.ton.block.AccountInfo
import org.ton.block.AccountNone
import org.ton.block.AddrStd
import org.ton.block.AddrVar
import org.ton.lite.api.exception.LiteServerException
import org.ton.lite.client.LiteClient
import org.ton.lite.client.internal.FullAccountState

class TonLiteClient(
    private val networkSwitcher: NetworkSwitcher,
    engine: HttpClientEngine,
) : NetworkSwitcherListener {

    private val json = Json { ignoreUnknownKeys = true }
    private var _liteClient: LiteClient? = null
    private val mutex = Mutex()
    private val httpClient = HttpClient(engine) {}

    init {
        networkSwitcher.watch(this)
    }

    private suspend fun liteClient(): LiteClient {
        val lC = _liteClient
        if (lC != null) {
            return lC
        }
        mutex.withLock {
            val config: LiteClientConfigGlobal =
                withContext(Dispatchers.Default) {
                    json.decodeFromString(
                        httpClient.get(
                            Url(
                                if (networkSwitcher.isTestnet)
                                    "https://ton.org/testnet-global.config.json"
                                else
                                    "https://ton.org/global-config.json",
                            )
                        ).bodyAsText()
                    )
                }
            val lc = _liteClient
            if (lc != null) {
                return lc
            }
            return LiteClient(Dispatchers.Default, config).also {
                _liteClient = it
            }
        }
    }

    private suspend fun <T> repeatOnLiteServerError(
        function: suspend LiteClient.() -> T
    ): Result<T> = kotlin.runCatching {
        repeat(10) {
            try {
                return Result.success(function.invoke(liteClient()))
            } catch (t: Throwable) {
                if (t is LiteServerException && t.code != -400) {
                    return Result.failure(t)
                }
            }
        }
        return Result.failure(Throwable(""))
    }


    suspend fun loadAccountInfo(accountAddressFriendly: String): Result<WalletInfo> =
        withContext(Dispatchers.Default) {
            repeatOnLiteServerError {
                val accountState =
                    getAccountState(AddrStd.parseUserFriendly(accountAddressFriendly))
                accountState.toWalletInfo(networkSwitcher.isTestnet)
            }
        }

    private fun FullAccountState.toWalletInfo(isTestnet: Boolean): WalletInfo =
        account.value.toWalletInfo(isTestnet)

    private fun Account.toWalletInfo(isTestnet: Boolean): WalletInfo =
        when (val account = this) {
            is AccountInfo -> {
                val accountAddressFriendly =
                    when (val addr = account.addr) {
                        is AddrStd -> addr.toString(userFriendly = true, testOnly = isTestnet)
                        is AddrVar -> addr.toString()
                    }
                val coins = account.storage.balance.coins
                WalletInfo(
                    address = accountAddressFriendly,
                    amount = coins.amount.toLong(),
                )
            }

            is AccountNone -> {
                WalletInfo(
                    address = "",
                    amount = 0L,
                )
            }
        }

    override fun onNetworkChanged(isTestnet: Boolean) {
        _liteClient = null
    }

}