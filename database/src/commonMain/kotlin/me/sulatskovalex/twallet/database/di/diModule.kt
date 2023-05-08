package me.sulatskovalex.twallet.database.di

import app.cash.sqldelight.db.SqlDriver
import me.sulatskovalex.twallet.database.TonWalletDatabase
import me.sulatskovalex.twallet.database.WalletDatabaseImpl
import me.sulatskovalex.twallet.database.WalletsDaoQueries
import me.sulatskovalex.twallet.database.createDriver
import me.sulatskovalex.twallet.domain.services.local.WalletDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

fun databaseModule(): Module = module {
    single<SqlDriver> { createDriver() }
    single<TonWalletDatabase> { TonWalletDatabase(get()) }
    single<WalletsDaoQueries> {
        val database = get<TonWalletDatabase>()
        database.walletsDaoQueries
    }
    single<WalletDatabase> { WalletDatabaseImpl(get()) }
}
