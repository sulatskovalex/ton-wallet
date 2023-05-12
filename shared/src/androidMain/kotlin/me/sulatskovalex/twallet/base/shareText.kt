package me.sulatskovalex.twallet.base

import android.content.Context
import android.content.Intent
import me.sulatskovalex.twallet.domain.common.inject

actual fun shareText(text: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    inject<Context>().startActivity(shareIntent)
}