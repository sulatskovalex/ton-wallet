package me.sulatskovalex.twallet.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual fun createDriver(): SqlDriver =
    NativeSqliteDriver(
        TonWalletDatabase.Schema,
        "twallet.db",
    )
