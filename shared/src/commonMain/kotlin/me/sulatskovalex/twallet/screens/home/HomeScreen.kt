package me.sulatskovalex.twallet.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import io.github.skeptick.libres.compose.painterResource
import me.sulatskovalex.twallet.common.Res

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {

    val selectedTab = remember { mutableStateOf(HomeTab.Home) }

    ModalBottomSheetLayout(
        {

        },
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = rememberScaffoldState(),
            topBar = {},
            bottomBar = {
                BottomNavigation(
                    modifier = Modifier.fillMaxWidth(),
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
            }
        ) {

        }
    }

}