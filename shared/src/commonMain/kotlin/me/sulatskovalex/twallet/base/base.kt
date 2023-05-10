package me.sulatskovalex.twallet.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.KViewModel
import com.adeo.kviewmodel.odyssey.StoredViewModel
import me.sulatskovalex.twallet.domain.common.inject
import me.sulatskovalex.twallet.providers.platform
import me.sulatskovalex.twallet.providers.appColors

@Composable
inline fun <reified T : KViewModel> SafeAreaScreen(
    safeAreaColor: Color = appColors.background,
    noinline content: @Composable (T) -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize().weight(1f)) {
            Screen<T>(content)
        }
        if (platform.iOS) {
            Box(
                Modifier.fillMaxWidth().height(34.dp).background(safeAreaColor)
            )
        }
    }
}

@Composable
inline fun <reified T : KViewModel> Screen(
    noinline content: @Composable (T) -> Unit
) =
    StoredViewModel({ inject<T>() }, "", content)

inline fun <reified T> MutableState<T>.update(updater: (T) -> T) {
    value = updater.invoke(this.value)
}