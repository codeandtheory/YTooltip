package com.app.ui.screen.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.navigation.NavHostController
import com.app.components.BottomAppBarView
import com.tooltip.ui.ToolTipScreen
import com.tooltip.ui.isTipVisible
import com.tooltip.utils.DEFAULT_CORNER_RADIUS
import com.tooltip.utils.TRANSPARENT_ALPHA

/**
 * @Composable Dashboard is parent component of this sample app.
 * @param navController is used to control navigation in child components.
 * */
@Composable
fun Dashboard(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        DashboardPage(navController)
    }
}

/**
 * @Composable DashboardPage is parent view of this sample app.
 * @param navController is used to control navigation in child view.
 * */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DashboardPage(navController: NavHostController) {
    //For fragments
    val selectedIndex = rememberSaveable { mutableStateOf(0) }
    val visibleHintCoordinates: MutableState<LayoutCoordinates?> = remember { mutableStateOf(null) }

    val isHintVisibleHome = remember { mutableStateOf(false) }
    val isHintVisibleFirstIndexHome = remember { mutableStateOf(true) }
    val isHintVisibleTechnology = remember { mutableStateOf(false) }
    val isHintVisibleScience = remember { mutableStateOf(false) }


    /**
     * @Composable ToolTipScreen is used to show ToolTip on screen with overlay.
     * Here we are doing configuration of ToolTipScreen.
     * mainContent is our full screen's content which can contains ToolTipViews.
     * anyHintVisible is used to find any hint visible on screen.
     * visibleHintCoordinates is used to find coordinates of visible Hint/ToolTip on screen.
     * Here BottomAppBarView contains ToolTipViews.
     */
    ToolTipScreen(
        paddingHighlightArea = 0f,
        backgroundTransparency = if (isHintVisibleTechnology.value) 0f else TRANSPARENT_ALPHA,
        cornerRadiusHighlightArea = if (isHintVisibleScience.value) 100f else DEFAULT_CORNER_RADIUS,
        mainContent = {
            Scaffold(
                bottomBar = {
                    BottomAppBarView(
                        selectedIndex = selectedIndex,
                        visibleHintCoordinates = visibleHintCoordinates,
                        isHintVisibleHome = isHintVisibleHome,
                        isHintVisibleTechnology = isHintVisibleTechnology,
                        isHintVisibleScience = isHintVisibleScience
                    )
                },
            ) {
                Body(
                    selectedIndex.value,
                    navController,
                    isHintVisibleFirstIndexHome,
                    visibleHintCoordinates
                )
            }
        },
        anyHintVisible = isTipVisible(
            isHintVisibleHome,
            isHintVisibleTechnology,
            isHintVisibleScience,
            isHintVisibleFirstIndexHome
        ).value,
        visibleHintCoordinates = visibleHintCoordinates,
    )
}

/**
 * @Composable Body is container of different tab contents.
 * @param selectedIndex is current selected index.
 * Body content will be updated according to selectedIndex.
 * @param navController is used to control navigation in child view.
 * @param isHintVisibleFirstIndexHome is used to handle visibility of first index item at home screen.
 * @param visibleHintCoordinates is used for visible hint coordinates.
 * */
@Composable
fun Body(
    selectedIndex: Int,
    navController: NavHostController,
    isHintVisibleFirstIndexHome: MutableState<Boolean>,
    visibleHintCoordinates: MutableState<LayoutCoordinates?>
) {
    when (selectedIndex) {
        0 -> {
            HomeScreen(
                navController = navController,
                isHintVisibleFirstIndexHome = isHintVisibleFirstIndexHome,
                visibleHintCoordinates = visibleHintCoordinates
            )
        }
        1 -> {
            TechnologyScreen(navController = navController)
        }
        2 -> {
            ScienceScreen(navController = navController)
        }
    }
}