package com.example.rssfeed.data.datasources

import com.example.rssfeed.data.model.News
import com.example.rssfeed.data.model.XmlArticle
import com.example.rssfeed.data.network.ApiService
import com.example.rssfeed.data.network.NetworkResult
import com.example.rssfeed.data.network.XmlNetworkHandler
import com.example.rssfeed.utils.XML_BASE_URL
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
    private val xmlNetworkHandler: XmlNetworkHandler
) : BaseRemoteDataSource() {

    suspend fun getJsonNewsListFromNetwork(page: Int): NetworkResult<News> = safeApiCall {
        jsonNewsListRequestBuilder(page)
    }

    suspend fun getXmlNewsFromNetwork(): List<XmlArticle> =
        xmlNetworkHandler.getXmNewsFromNetwork(XML_BASE_URL)

    private suspend fun jsonNewsListRequestBuilder(page: Int = 1): Response<News> {
        return apiService.getJsonNewsList(
            domains = "wired.com",
            apiKey = "6b893964ebd740feafd48eb69e42edd1",
            page = page
        )
    }

}