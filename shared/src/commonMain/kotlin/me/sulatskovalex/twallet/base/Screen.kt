package me.sulatskovalex.twallet.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.KViewModel
import com.adeo.kviewmodel.odyssey.StoredViewModel
import com.adeo.kviewmodel.odyssey.setupWithViewModels
import me.sulatskovalex.twallet.domain.common.inject
import me.sulatskovalex.twallet.providers.appColors
import me.sulatskovalex.twallet.providers.platform
import ru.alexgladkov.odyssey.compose.local.LocalRootController

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
inline fun SafeAreaDialogScreen(
    backgroundColor: Color = appColors.surface,
    safeAreaColor: Color = appColors.background,
    noinline content: @Composable () -> Unit,
) =
    Column(Modifier.fillMaxWidth().background(backgroundColor)) {
        Box(Modifier.fillMaxWidth()) {
            content.invoke()
        }
        when {
            platform.android ->
                Box(Modifier.fillMaxWidth().height(48.dp).background(safeAreaColor))

            platform.iOS ->
                Box(Modifier.fillMaxWidth().height(34.dp).background(safeAreaColor))
        }
    }

@Composable
inline fun <reified T : KViewModel> Screen(
    noinline content: @Composable (T) -> Unit
) {
    val viewModel = remember { inject<T>() }
    LocalRootController.current.setupWithViewModels()
    StoredViewModel({ viewModel }, "", content)
}
