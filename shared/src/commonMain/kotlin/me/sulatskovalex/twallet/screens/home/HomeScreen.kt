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
import io.github.skeptick.libres.compose.painterResource
import me.sulatskovalex.twallet.base.SafeAreaScreen
import me.sulatskovalex.twallet.common.Res
import me.sulatskovalex.twallet.screens.home.home.assets.AssetsScreen
import me.sulatskovalex.twallet.screens.home.settings.SettingsScreen
import me.sulatskovalex.twallet.providers.appColors

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
                        {
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
                        HomeTab.Settings -> SettingsScreen(onGotoSplash)
                    }
                }
            }
        }
    }
}
