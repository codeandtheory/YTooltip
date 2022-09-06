package com.app.data.repository

import com.app.data.model.NewsByCategoryRes
import com.app.data.remote.AppConfig
import com.app.data.remote.NetworkManager
import com.yml.network.core.request.DataRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class NewsRepo {
    fun getTopNewsByCategory(category: String) = NetworkManager.instance
        .submit(
            DataRequest.get(
                "${AppConfig.baseurl}${AppConfig.topHeadlines}?country=${AppConfig.country}&category=$category&apiKey=${AppConfig.apiKey}",
                NewsByCategoryRes::class
            )
        ).asFlow().flowOn(Dispatchers.IO)
}