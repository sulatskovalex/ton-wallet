package me.sulatskovalex.twallet.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.DefaultFillType
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kotlin.math.ceil
import kotlin.math.sqrt

val tonIcon: ImageVector
    @Composable
    get() {
        if (!::_tonIcon.isInitialized) {
            val sizeDp = 56.dp
            val size = 56f
            _tonIcon =
                ImageVector.Builder(
                    name = "TON",
                    defaultWidth = sizeDp,
                    defaultHeight = sizeDp,
                    viewportWidth = size,
                    viewportHeight = size
                ).apply {
                    val x = ceil(sqrt(size)) + 0.2f
                    val radius = (size - x - x) / 2
                    path("circle", SolidColor(Color(0xFF0088CC))) {
                        moveTo(x, x)
                        arcTo(
                            horizontalEllipseRadius = radius,
                            verticalEllipseRadius = radius,
                            theta = 180f,
                            isMoreThanHalf = false,
                            isPositiveArc = true,
                            x1 = size - x,
                            y1 = size - x,
                        )
                        moveTo(size - x, size - x)
                        arcTo(
                            horizontalEllipseRadius = radius,
                            verticalEllipseRadius = radius,
                            theta = 180f,
                            isMoreThanHalf = false,
                            isPositiveArc = true,
                            x1 = x,
                            y1 = x,
                        )
                    }
                    path(
                        fill = SolidColor(Color.White),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = DefaultFillType,
                    ) {
                        moveTo(20.2088f, 18.5044f)
                        lineTo(35.9132f, 18.5043f)
                        curveTo(36.4688f, 18.5043f, 37.024f, 18.5859f, 37.6042f, 18.8564f)
                        curveTo(38.2997f, 19.1806f, 38.6685f, 19.6916f, 38.9269f, 20.0695f)
                        curveTo(38.947f, 20.0989f, 38.9658f, 20.1292f, 38.9832f, 20.1602f)
                        curveTo(39.287f, 20.701f, 39.4436f, 21.2849f, 39.4436f, 21.913f)
                        curveTo(39.4436f, 22.5098f, 39.3016f, 23.16f, 38.9832f, 23.7267f)
                        curveTo(38.9802f, 23.7322f, 38.9771f, 23.7375f, 38.974f, 23.7429f)
                        lineTo(29.0522f, 40.7864f)
                        curveTo(28.8334f, 41.1623f, 28.4307f, 41.3928f, 27.9958f, 41.3913f)
                        curveTo(27.5609f, 41.3898f, 27.1598f, 41.1563f, 26.9437f, 40.7789f)
                        lineTo(17.2041f, 23.7718f)
                        curveTo(17.2013f, 23.7672f, 17.1985f, 23.7626f, 17.1957f, 23.7579f)
                        curveTo(16.9728f, 23.3906f, 16.6281f, 22.8226f, 16.5678f, 22.0896f)
                        curveTo(16.5124f, 21.4155f, 16.6639f, 20.7401f, 17.0026f, 20.1545f)
                        curveTo(17.3413f, 19.5688f, 17.8512f, 19.1006f, 18.4645f, 18.814f)
                        curveTo(19.1221f, 18.5067f, 19.7885f, 18.5044f, 20.2088f, 18.5044f)
                        close()
                        moveTo(26.7827f, 20.9391f)
                        lineTo(20.2088f, 20.9391f)
                        curveTo(19.7769f, 20.9391f, 19.6111f, 20.9657f, 19.4952f, 21.0199f)
                        curveTo(19.3349f, 21.0947f, 19.2003f, 21.2178f, 19.1103f, 21.3734f)
                        curveTo(19.0203f, 21.5291f, 18.9796f, 21.7095f, 18.9944f, 21.8901f)
                        curveTo(19.0029f, 21.9936f, 19.0451f, 22.112f, 19.294f, 22.5225f)
                        curveTo(19.2992f, 22.5311f, 19.3043f, 22.5398f, 19.3093f, 22.5485f)
                        lineTo(26.7827f, 35.5984f)
                        verticalLineTo(20.9391f)
                        close()
                        moveTo(29.2175f, 20.9391f)
                        verticalLineTo(35.6629f)
                        lineTo(36.864f, 22.5278f)
                        curveTo(36.9503f, 22.371f, 37.0088f, 22.1444f, 37.0088f, 21.913f)
                        curveTo(37.0088f, 21.7253f, 36.9699f, 21.5623f, 36.8829f, 21.3943f)
                        curveTo(36.7916f, 21.263f, 36.736f, 21.1935f, 36.6895f, 21.1459f)
                        curveTo(36.6496f, 21.1052f, 36.6189f, 21.0834f, 36.5755f, 21.0632f)
                        curveTo(36.3947f, 20.9789f, 36.2097f, 20.9391f, 35.9132f, 20.9391f)
                        lineTo(29.2175f, 20.9391f)
                        close()

                    }
                }.build()
        }

        return _tonIcon
    }

private lateinit var _tonIcon: ImageVector