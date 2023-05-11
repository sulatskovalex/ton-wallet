package me.sulatskovalex.twallet.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.sulatskovalex.twallet.providers.appColors

@Composable
fun BottomSheetHeader(onCloseClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .padding(start = 8.dp, top = 8.dp, end = 8.dp),
    ) {
        IconButton(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.TopEnd),
            onClick = onCloseClick,
        ) {
            Icon(
                contentDescription = "Close",
                imageVector = Icons.Default.Close,
                tint = appColors.primaryText,
            )
        }
        Box(
            modifier = Modifier
                .width(48.dp)
                .height(5.dp)
                .align(Alignment.TopCenter)
                .background(
                    color = Color.Gray,
                    shape = RoundedCornerShape(2.dp)
                ),
        )
    }
}
