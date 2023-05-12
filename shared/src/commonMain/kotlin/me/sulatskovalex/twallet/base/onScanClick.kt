package me.sulatskovalex.twallet.base

import ru.alexgladkov.odyssey.compose.controllers.ModalController

expect fun ModalController.showScanQRDialog(onScan: (scanned: String, onClose: () -> Unit) -> Unit)
