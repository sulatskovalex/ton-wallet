package me.sulatskovalex.twallet.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
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
import io.github.skeptick.libres.compose.painterResource
import me.sulatskovalex.twallet.AppScreens
import me.sulatskovalex.twallet.base.AlertButton
import me.sulatskovalex.twallet.base.AlertDialog
import me.sulatskovalex.twallet.base.SafeAreaScreen
import me.sulatskovalex.twallet.base.showScanQRDialog
import me.sulatskovalex.twallet.common.Res
import me.sulatskovalex.twallet.providers.appColors
import me.sulatskovalex.twallet.screens.home.assets.AssetsScreen
import me.sulatskovalex.twallet.screens.home.settings.SettingsScreen
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.controllers.ModalController
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import ru.alexgladkov.odyssey.core.LaunchFlag

@Composable
fun HomeScreen(
    controller: RootController = LocalRootController.current,
    modalController: ModalController = controller.findModalController(),
) {
    SafeAreaScreen<HomeViewModel>(appColors.surface) { viewModel ->
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
                    HomeTab.Home ->
                        AssetsScreen(
                            onSendClick = {
                                modalController.showSendDialog(
                                    onScanClick = { onAddressScanned ->
                                        modalController.showScanQRDialog(
                                            onScan = { scanned, onClose ->
                                                val address = viewModel.validateAddress(scanned)
                                                if (address != null) {
                                                    onAddressScanned.invoke(address)
                                                    onClose.invoke()
                                                }
                                            },
                                        )
                                    })
                            },
                            onReceiveClick = { addressFriendly ->
                                modalController.showReceiveDialog(
                                    addressFriendly = addressFriendly,
                                )
                            }
                        )

                    HomeTab.Settings ->
                        SettingsScreen(
                            onExitClick = {
                                modalController.showDisconnectWalletDialog(
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

private inline fun ModalController.showDisconnectWalletDialog(
    noinline onDisconnectOkClick: (dialogKey: String) -> Unit,
) = present(AlertConfiguration(cornerRadius = 4)) { key ->
    AlertDialog(
        titleText = Res.string.disconnect,
        titleTextColor = appColors.error,
        messageText = Res.string.disconnect_alert_message,
        messageTextColor = appColors.secondaryText,
        positive = AlertButton(Res.string.disconnect, appColors.error) {
            popBackStack(key)
            onDisconnectOkClick.invoke(key)
        },
        negative = AlertButton(Res.string.cancel, appColors.secondaryText) {
            popBackStack(key)
        },
    )
}
