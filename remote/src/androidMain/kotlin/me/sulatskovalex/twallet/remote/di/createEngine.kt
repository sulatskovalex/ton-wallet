package me.sulatskovalex.twallet.remote.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android

actual fun createEngine(): HttpClientEngine = Android.create {  }
