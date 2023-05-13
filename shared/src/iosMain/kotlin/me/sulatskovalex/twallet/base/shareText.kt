package me.sulatskovalex.twallet.base

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

actual fun shareText(text: String) {
    UIApplication.sharedApplication
        .keyWindow
        ?.rootViewController
        ?.presentViewController(
            viewControllerToPresent = UIActivityViewController(
                activityItems = listOf(text),
                applicationActivities = null
            ),
            animated = true,
            completion = null
        )
}