package me.sulatskovalex.twallet.base

import platform.UIKit.UIApplication
import platform.UIKit.UIModalPresentationPageSheet
import platform.UIKit.UINavigationController
import platform.UIKit.modalInPresentation
import ru.alexgladkov.odyssey.compose.controllers.ModalController


actual fun ModalController.showScanQRDialog(onScan: (scanned: String, onClose: () -> Unit) -> Unit) {
    UIApplication.sharedApplication
        .keyWindow
        ?.rootViewController
        ?.presentViewController(
            viewControllerToPresent = UINavigationController(
                rootViewController = ScannerViewController(onScan),
            ).apply {
                modalInPresentation = true
                modalPresentationStyle = UIModalPresentationPageSheet
            },
            animated = true,
            completion = null,
        )
}
