package me.sulatskovalex.twallet.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.sulatskovalex.twallet.providers.appColors
import me.sulatskovalex.twallet.providers.displaySize

class AlertButton(
    val text: String,
    val color: Color,
    val onClick: () -> Unit,
)

@Composable
inline fun AlertDialog(
    titleText: String,
    messageText: String,
    positive: AlertButton,
    titleTextColor: Color = appColors.primaryText,
    messageTextColor: Color = appColors.secondaryText,
    negative: AlertButton? = null,
) =
    Column(
        Modifier
            .width((displaySize.widthDp.toFloat() / 1.3).dp)
            .background(appColors.surface)
            .padding(16.dp),
    ) {
        Text(
            text = titleText,
            fontSize = 18.sp,
            color = titleTextColor
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = messageText,
            fontSize = 16.sp,
            color = messageTextColor
        )
        Spacer(Modifier.height(16.dp))
        Row(
            Modifier.align(Alignment.End),
        ) {
            if (negative != null) {
                TextButton(negative.onClick) {
                    Text(
                        text = negative.text,
                        fontSize = 16.sp,
                        color = negative.color,
                    )
                }
                Spacer(Modifier.width(16.dp))
            }
            TextButton(positive.onClick) {
                Text(
                    text = positive.text,
                    fontSize = 16.sp,
                    color = positive.color
                )
            }
        }
    }
