package com.app.ui.screen.dashboard


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.app.components.ArticleView
import com.app.data.remote.AppConfig
import com.app.data.repository.NewsRepo
import com.app.ui.theme.DEFAULT_INNER_PADDING
import com.app.ui.theme.PADDING_BOTTOM_APPBAR
import com.app.viewmodel.MainViewModel
import com.app.viewmodel.MainViewModelFactory

@Composable
fun TechnologyScreen(navController: NavHostController) {
    val myViewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(NewsRepo()))

    LaunchedEffect(Unit, block = {
        myViewModel.getTopNewsByCategory(AppConfig.technology)
    })

    val topNewsByCategory = myViewModel.newsByCategoryRes.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            item {
                Spacer(modifier = Modifier.padding(DEFAULT_INNER_PADDING))
            }
            itemsIndexed(topNewsByCategory.value.articles) { index, article ->
                ArticleView(article = article)
            }
            item {
                Spacer(modifier = Modifier.padding(PADDING_BOTTOM_APPBAR))
            }
        }
    }
}