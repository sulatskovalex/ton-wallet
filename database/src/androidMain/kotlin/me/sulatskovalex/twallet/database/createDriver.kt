package me.sulatskovalex.twallet.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import me.sulatskovalex.twallet.domain.di.inject

actual fun createDriver(): SqlDriver =
    AndroidSqliteDriver(
        TonWalletDatabase.Schema,
        inject(),
        "twallet.db",
    )
