package com.app.data.remote

import android.util.Log
import com.accelerator.network.android.engine.network.AndroidNetworkEngine
import com.google.gson.Gson
import com.yml.network.core.NetworkManagerBuilder
import com.yml.network.core.interceptors.Interceptor
import com.yml.network.core.parser.BasicDataParserFactory
import com.yml.network.core.parser.DataParser
import com.yml.network.core.request.DataRequest
import kotlin.reflect.KClass


object NetworkManager {

    private val networkEngine by lazy {
        AndroidNetworkEngine()/*.init(NetworkEngineConfiguration())*/
    }

    val instance = NetworkManagerBuilder(
        networkEngine,
        BasicDataParserFactory.json(JsonDataParser())
    ).setBasePath(AppConfig.baseurl)
        .setInterceptors(mutableListOf<Interceptor>().apply {
            add(APITokenInterceptor())
        })
        .build()
}

private class APITokenInterceptor : Interceptor {
    override fun onParsedRequest(request: DataRequest<String>): DataRequest<String> {
        val requestPath = request.requestPath
        requestPath.queryParams.add(Pair("apiKey", AppConfig.apiKey))
        Log.d("NetworkManager", "${request.requestPath}")
        return request
    }
}

class JsonDataParser : DataParser {

    private val gson = Gson()

    /**
     * Serialize the give object of DATA type to string.
     */
    override fun <DATA : Any> serialize(data: DATA): String = gson.toJson(data, data.javaClass)

    /**
     * Deserialize the response string to expected DATA type object.
     */
    override fun <DATA : Any> deserialize(data: String, kClass: KClass<DATA>): DATA =
        gson.fromJson(data, kClass.java)
}
