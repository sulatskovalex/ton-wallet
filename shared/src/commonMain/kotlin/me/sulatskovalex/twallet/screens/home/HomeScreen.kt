package me.sulatskovalex.twallet.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.skeptick.libres.compose.painterResource
import me.sulatskovalex.twallet.base.SafeAreaScreen
import me.sulatskovalex.twallet.common.Res
import me.sulatskovalex.twallet.providers.appColors
import me.sulatskovalex.twallet.providers.displaySize
import me.sulatskovalex.twallet.screens.home.home.assets.AssetsScreen
import me.sulatskovalex.twallet.screens.home.settings.SettingsScreen
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.controllers.ModalController
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import ru.alexgladkov.odyssey.core.LaunchFlag

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    onGotoSplash: () -> Unit,
) {
    SafeAreaScreen<HomeViewModel>(
        appColors.surface
    ) {
        ModalBottomSheetLayout(
            sheetContent = {

            },
            modifier = Modifier.fillMaxSize(),
        ) {
            val selectedTab = remember { mutableStateOf(HomeTab.Home) }
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                scaffoldState = rememberScaffoldState(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = when (selectedTab.value) {
                                    HomeTab.Home -> Res.string.menu_wallet
                                    HomeTab.Settings -> Res.string.menu_settings
                                },
                                color = appColors.primaryText,
                            )
                        },
                        backgroundColor = appColors.surface,
                    )
                },
                bottomBar = {
                    BottomNavigation(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = appColors.surface,
                    ) {
                        BottomNavigationItem(
                            selected = selectedTab.value == HomeTab.Home,
                            onClick = {
                                selectedTab.value = HomeTab.Home
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(Res.image.ic_wallet),
                                    contentDescription = Res.string.menu_wallet,
                                )
                            },
                            selectedContentColor = appColors.primary,
                            unselectedContentColor = appColors.disabledText,
                        )
                        BottomNavigationItem(
                            selected = selectedTab.value == HomeTab.Settings,
                            onClick = {
                                selectedTab.value = HomeTab.Settings
                            },
                            icon = {
                                Icon(
                                    painter = rememberVectorPainter(Icons.Default.Settings),
                                    contentDescription = Res.string.menu_settings,
                                )
                            },
                            selectedContentColor = appColors.primary,
                            unselectedContentColor = appColors.disabledText,
                        )
                    }
                },
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .background(appColors.background),
                ) {
                    when (selectedTab.value) {
                        HomeTab.Home -> AssetsScreen()

                        HomeTab.Settings ->
                            SettingsScreen(
                                onExitClick = {
                                    modalController.showDisconnectWallet(
                                        onDisconnectOkClick = { key ->
                                            viewModel.onExitClick {
                                                modalController.popBackStack(key)
                                                controller.launch(
                                                    screen = AppScreens.Splash.name,
                                                    launchFlag = LaunchFlag.SingleNewTask,
                                                )
                                            }
                                        },
                                    )
                                }
                            )
                    }
                }
            }
        }
    }
}

inline fun ModalController.showDisconnectWallet(
    noinline onDisconnectOkClick: (dialogKey: String) -> Unit,
) = present(AlertConfiguration(cornerRadius = 4)) { key ->
    Column(
        Modifier
            .width((displaySize.widthDp.toFloat() / 1.3).dp)
            .background(appColors.surface)
            .padding(16.dp),
    ) {
        Text(
            text = Res.string.disconnect,
            fontSize = 18.sp,
            color = appColors.error
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = Res.string.disconnect_alert_message,
            fontSize = 16.sp,
            color = appColors.secondaryText
        )
        Spacer(Modifier.height(16.dp))
        Row(
            Modifier.align(Alignment.End),
        ) {
            TextButton({
                popBackStack(key)
            }) {
                Text(text = Res.string.cancel, fontSize = 16.sp, color = appColors.secondaryText)
            }
            Spacer(Modifier.width(16.dp))
            TextButton({
                onDisconnectOkClick(key)
            }) {
                Text(
                    text = Res.string.disconnect,
                    fontSize = 16.sp,
                    color = appColors.error
                )
            }
        }
    }
}

