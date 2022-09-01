package com.app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.data.model.NewsByCategoryRes
import com.app.data.repository.NewsRepo
import com.yml.network.core.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(private val newsRepository: NewsRepo) : ViewModel() {
    private val _newsByCategoryRes = MutableStateFlow(NewsByCategoryRes())
    val newsByCategoryRes: StateFlow<NewsByCategoryRes> get() = _newsByCategoryRes

    fun getTopNewsByCategory(category: String) {
        viewModelScope.launch {
            newsRepository.getTopNewsByCategory(category).collectLatest {
                when (it) {
                    is Resource.Error -> {
                        Log.d("NewsViewModel", "Data fetching failed:${it.error}")
                    }
                    is Resource.Success -> {
                        Log.d("NewsViewModel", "Data fetching success:${it.data.body}")
                        it.data.body?.let { it1 -> _newsByCategoryRes.emit(it1) }
                    }
                }
            }
        }
    }
}

class MainViewModelFactory(private val newsRepository: NewsRepo) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainViewModel(newsRepository) as T
}