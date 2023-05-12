package me.sulatskovalex.twallet.base

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

actual fun shareText(text: String) {
    UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
        UIActivityViewController(listOf(text), null),
        true,
        null
    )
}