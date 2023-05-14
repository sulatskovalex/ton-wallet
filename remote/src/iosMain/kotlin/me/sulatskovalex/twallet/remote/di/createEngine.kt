package me.sulatskovalex.twallet.remote.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual fun createEngine(): HttpClientEngine = Darwin.create {}
