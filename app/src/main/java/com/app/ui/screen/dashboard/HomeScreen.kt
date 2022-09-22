package com.app.ui.screen.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import co.yml.tooltip.ToolTipView
import co.yml.tooltip.utils.TOOLTIP_ADDITIONAL_PADDING
import co.yml.tooltip.utils.ToolTipGravity
import com.app.components.ArticleView
import com.app.components.ToolTipContentWithIcon
import com.app.data.remote.AppConfig
import com.app.data.repository.NewsRepo
import com.app.ui.theme.DEFAULT_INNER_PADDING
import com.app.ui.theme.PADDING_BOTTOM_APPBAR
import com.app.ui.theme.ToolTipBg
import com.app.viewmodel.MainViewModel
import com.app.viewmodel.MainViewModelFactory

@Composable
fun HomeScreen(
    navController: NavHostController,
    isHintVisibleFirstIndexHome: MutableState<Boolean>,
    visibleHintCoordinates: MutableState<LayoutCoordinates?>
) {
    val myViewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(NewsRepo()))

    LaunchedEffect(Unit, block = {
        myViewModel.getTopNewsByCategory(AppConfig.general)
    })

    val topNewsByCategory = myViewModel.newsByCategoryRes.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            item {
                Spacer(modifier = Modifier.padding(DEFAULT_INNER_PADDING))
            }
            itemsIndexed(topNewsByCategory.value.articles) { index, article ->
                if (index == 0)
                    ToolTipView(
                        visibleHintCoordinates = visibleHintCoordinates,
                        isHintVisible = isHintVisibleFirstIndexHome,
                        dismissOnTouchOutside = true,
                        hintBackgroundColor = ToolTipBg,
                        hintGravity = ToolTipGravity.BOTTOM,
                        modifier = Modifier.padding(TOOLTIP_ADDITIONAL_PADDING),
                        customHintContent = {
                            ToolTipContentWithIcon(
                                hintText = "${article.description} ",
                                isHintVisible = isHintVisibleFirstIndexHome,
                                imageVector = Icons.Filled.Info
                            )
                        },
                        onClick = {

                        },
                        customViewClickable = {
                            ArticleView(article = article)
                        }
                    )
                if (index != 0)
                    ArticleView(article = article)

            }
            item {
                Spacer(modifier = Modifier.padding(PADDING_BOTTOM_APPBAR))
            }
        }
    }
}