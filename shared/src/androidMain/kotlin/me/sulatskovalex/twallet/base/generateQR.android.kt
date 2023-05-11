package me.sulatskovalex.twallet.base

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

actual fun generateQR(content: String, sizePx: Int): ImageBitmap? {
    try {
        val qrCodeWriter = QRCodeWriter()

        val encodeHints = mutableMapOf<EncodeHintType, Any?>()

        val bitmapMatrix = try {
            qrCodeWriter.encode(
                content, BarcodeFormat.QR_CODE,
                sizePx, sizePx, encodeHints
            )
        } catch (ex: WriterException) {
            null
        }

        val matrixWidth = bitmapMatrix?.width ?: sizePx
        val matrixHeight = bitmapMatrix?.height ?: sizePx

        val newBitmap = Bitmap.createBitmap(
            bitmapMatrix?.width ?: sizePx,
            bitmapMatrix?.height ?: sizePx,
            Bitmap.Config.ARGB_8888,
        )

        for (x in 0 until matrixWidth) {
            for (y in 0 until matrixHeight) {
                val shouldColorPixel = bitmapMatrix?.get(x, y) ?: false
                val pixelColor = if (shouldColorPixel) Color.BLACK else Color.WHITE
                newBitmap.setPixel(x, y, pixelColor)
            }
        }
        return newBitmap.asImageBitmap()
    } catch (t: Throwable) {
        t.printStackTrace()
    }
    return null
}
