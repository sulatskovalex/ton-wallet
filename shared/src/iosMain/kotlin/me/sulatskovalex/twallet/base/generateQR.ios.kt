package me.sulatskovalex.twallet.base

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.useContents
import kotlinx.cinterop.usePinned
import org.jetbrains.skia.Image
import platform.CoreGraphics.CGAffineTransformMakeScale
import platform.CoreImage.CIFilter
import platform.CoreImage.filterWithName
import platform.Foundation.NSASCIIStringEncoding
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.dataUsingEncoding
import platform.Foundation.setValue
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation
import platform.posix.memcpy

actual fun generateQR(content: String, sizePx: Int): ImageBitmap? {
    val filter = CIFilter.filterWithName("CIQRCodeGenerator") ?: return null
    val data = (content as? NSString)?.dataUsingEncoding(NSASCIIStringEncoding) ?: return null
    filter.setValue(data, forKey = "inputMessage")
    val out = filter.outputImage ?: return null

    val transform = CGAffineTransformMakeScale(
        (sizePx / out.extent.useContents { size.width }),
        (sizePx / out.extent.useContents { size.width }),
    )
    val output = out.imageByApplyingTransform(transform)
    return UIImagePNGRepresentation(UIImage(cIImage = output))?.byteArray?.let {
        Image.makeFromEncoded(it)
    }?.toComposeImageBitmap()
}

val NSData.byteArray: ByteArray
    get() = ByteArray(this@byteArray.length.toInt()).apply {
        usePinned {
            memcpy(it.addressOf(0), this@byteArray.bytes, this@byteArray.length)
        }
    }
