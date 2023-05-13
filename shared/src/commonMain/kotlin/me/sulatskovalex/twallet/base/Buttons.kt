package me.sulatskovalex.twallet.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import me.sulatskovalex.twallet.providers.appColors

@Composable
inline fun OutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = appColors.buttonOutlineBackground,
    borderColor: Color = appColors.primary,
    textColor: Color = appColors.primary,
    noinline onClick: () -> Unit,
) =
    androidx.compose.material.OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = backgroundColor,
        ),
        border = BorderStroke(1.dp, borderColor)

    ) {
        Text(
            text = text,
            color = textColor,
        )
    }

@Composable
inline fun Button(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = appColors.buttonBackground,
    textColor: Color = appColors.buttonText,
    isEnabled: Boolean = true,
    noinline onClick: () -> Unit,
) =
    androidx.compose.material.Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
        ),
        enabled = isEnabled,
    ) {
        Text(
            text = text,
            color = textColor,
        )
    }

@Composable
inline fun LoadingButton(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean,
    backgroundColor: Color = appColors.buttonBackground,
    textColor: Color = appColors.buttonText,
    noinline onClick: () -> Unit,
) =
    androidx.compose.material.Button(
        modifier = modifier,
        onClick = {
            if (!isLoading) {
                onClick.invoke()
            }
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                Modifier.align(Alignment.CenterVertically).size(20.dp),
                color = textColor,
                strokeWidth = 2.dp,
            )
        } else {
            Text(
                text = text,
                color = textColor,
            )
        }
    }

@Composable
inline fun IconButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String = "",
    tint: Color = appColors.primaryText,
    noinline onClick: () -> Unit
) =
    androidx.compose.material.IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            tint = tint,
        )
    }
