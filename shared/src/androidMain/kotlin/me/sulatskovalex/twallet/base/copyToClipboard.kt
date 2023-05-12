package me.sulatskovalex.twallet.base

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import me.sulatskovalex.twallet.domain.common.inject

actual fun copyToClipboard(text: String) {
    val context = inject<Context>()
    val clipboardManager = context.getSystemService(ClipboardManager::class.java)
    clipboardManager.setPrimaryClip(ClipData.newPlainText("", text))
}