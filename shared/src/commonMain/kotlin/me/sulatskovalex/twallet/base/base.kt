package me.sulatskovalex.twallet.base

import androidx.compose.runtime.Composable
import com.adeo.kviewmodel.KViewModel
import com.adeo.kviewmodel.odyssey.StoredViewModel
import me.sulatskovalex.twallet.domain.di.inject

@Composable
inline fun <reified T : KViewModel> Screen(
    noinline content: @Composable (T) -> Unit,
) = StoredViewModel({ inject<T>() }, "", content)