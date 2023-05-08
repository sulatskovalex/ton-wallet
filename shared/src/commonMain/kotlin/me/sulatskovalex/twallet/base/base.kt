package me.sulatskovalex.twallet.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.adeo.kviewmodel.KViewModel
import com.adeo.kviewmodel.odyssey.StoredViewModel
import me.sulatskovalex.twallet.domain.common.inject

@Composable
inline fun <reified T : KViewModel> Screen(
    noinline content: @Composable (T) -> Unit,
) = StoredViewModel({ inject<T>() }, "", content)

inline fun <reified T> MutableState<T>.update(updater: (T) -> T) {
    value = updater.invoke(this.value)
}