package me.sulatskovalex.twallet.screens.start.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import me.sulatskovalex.twallet.AppScreens
import me.sulatskovalex.twallet.base.Button
import me.sulatskovalex.twallet.base.IconButton
import me.sulatskovalex.twallet.base.SafeAreaScreen
import me.sulatskovalex.twallet.common.Res
import me.sulatskovalex.twallet.providers.appColors
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.LaunchFlag

@Composable
fun InputSeedScreen(
    controller: RootController = LocalRootController.current
) =
    SafeAreaScreen<InputSeedViewModel> { viewModel ->
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = Res.string.import_wallet,
                            color = appColors.primaryText,
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    navigationIcon = {
                        IconButton(
                            painter = rememberVectorPainter(Icons.Default.ArrowBack),
                            onClick = controller::popBackStack,
                        )
                    },
                    backgroundColor = appColors.surface,
                )
            },
            backgroundColor = appColors.background,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp)
                    .background(appColors.background),
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                    text = Res.string.next,
                    onClick = {
                        controller.launch(
                            screen = AppScreens.Home.name,
                            launchFlag = LaunchFlag.SingleNewTask,
                        )
                    },
                )
            }
        }
    }