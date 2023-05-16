package me.sulatskovalex.twallet.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role

inline fun <reified T> MutableState<T>.update(updater: (T) -> T) {
    value = updater.invoke(this.value)
}

fun Modifier.rippleClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
): Modifier = composed {
    clickable(
        remember { MutableInteractionSource() },
        rememberRipple(),
        enabled,
        onClickLabel,
        role,
        onClick
    )
}
