package me.sulatskovalex.twallet.base

import androidx.compose.ui.graphics.ImageBitmap

expect fun generateQR(content: String, sizePx: Int): ImageBitmap?
