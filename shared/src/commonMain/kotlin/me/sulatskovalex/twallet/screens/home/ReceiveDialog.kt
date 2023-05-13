package me.sulatskovalex.twallet.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.skeptick.libres.compose.painterResource
import me.sulatskovalex.twallet.base.BottomSheetHeader
import me.sulatskovalex.twallet.base.IconButton
import me.sulatskovalex.twallet.base.copyToClipboard
import me.sulatskovalex.twallet.base.rememberQrBitmapPainter
import me.sulatskovalex.twallet.base.shareText
import me.sulatskovalex.twallet.common.Res
import me.sulatskovalex.twallet.providers.appColors
import me.sulatskovalex.twallet.providers.displaySize
import ru.alexgladkov.odyssey.compose.controllers.ModalController
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalSheetConfiguration

inline fun ModalController.showReceiveDialog(
    addressFriendly: String,
) =
    present(
        ModalSheetConfiguration(cornerRadius = 8)
    ) { key ->
        Column(Modifier.background(appColors.surface)) {
            BottomSheetHeader { popBackStack(key) }
            val width = (displaySize.widthDp / 1.3).dp
            Column(
                modifier = Modifier
                    .width(width)
                    .align(Alignment.CenterHorizontally)
                    .background(appColors.background, RoundedCornerShape(16.dp))
                    .padding(all = 16.dp),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Scan QR code to receive coins",
                    color = appColors.secondaryText,
                    fontSize = 16.sp,
                )
                Spacer(Modifier.height(8.dp))
                val imageSize = width - 32.dp
                Image(
                    modifier = Modifier
                        .size(imageSize)
                        .clip(RoundedCornerShape(8.dp)),
                    painter = rememberQrBitmapPainter(
                        content = addressFriendly,
                        imageSize,
                    ),
                    contentDescription = "qr",
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Or use wallet address",
                    color = appColors.secondaryText,
                    fontSize = 16.sp,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = addressFriendly,
                    color = appColors.primaryText,
                    fontSize = 16.sp,
                )
            }
            Spacer(Modifier.height(8.dp))
            Row(Modifier.align(Alignment.CenterHorizontally)) {
                Column {
                    IconButton(
                        modifier = Modifier
                            .background(appColors.primary, RoundedCornerShape(24.dp))
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(Res.image.ic_copy),
                        onClick = { copyToClipboard(addressFriendly) },
                        tint = Color.White,
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = Res.string.copy,
                        fontSize = 14.sp,
                        color = appColors.primaryText
                    )
                }
                Spacer(Modifier.width(32.dp))
                Column {
                    IconButton(
                        modifier = Modifier
                            .background(appColors.primary, RoundedCornerShape(24.dp))
                            .align(Alignment.CenterHorizontally),
                        painter = rememberVectorPainter(Icons.Default.Share),
                        onClick = {
                            shareText(addressFriendly)
                        },
                        tint = Color.White,
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = Res.string.share,
                        fontSize = 14.sp,
                        color = appColors.primaryText
                    )
                }
            }
            Spacer(Modifier.height(32.dp))
        }
    }


