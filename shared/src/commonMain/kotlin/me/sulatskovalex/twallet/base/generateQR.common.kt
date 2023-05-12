package me.sulatskovalex.twallet.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

expect fun generateQR(content: String, sizePx: Int): ImageBitmap?

@Composable
fun rememberQrBitmapPainter(
    content: String,
    sideSize: Dp
): BitmapPainter {
    val density = LocalDensity.current

    val sizePx = with(density) { sideSize.roundToPx() }


    val bitmap = remember(content) {
        mutableStateOf<ImageBitmap?>(null)
    }

    LaunchedEffect(bitmap) {
        if (content.isEmpty() || bitmap.value != null) return@LaunchedEffect
        bitmap.value = withContext(Dispatchers.Default) { generateQR(content, sizePx) }
    }

    return remember(bitmap.value) {
        val value = bitmap.value ?: ImageBitmap(sizePx, sizePx)
        BitmapPainter(
            value,
            IntOffset.Zero,
            IntSize(value.width, value.height),
            FilterQuality.High
        )
    }
}
