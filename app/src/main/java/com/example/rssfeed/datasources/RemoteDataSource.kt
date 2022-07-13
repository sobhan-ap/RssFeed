package com.example.rssfeed.datasources

import com.example.rssfeed.data.model.News
import com.example.rssfeed.data.model.XmlArticle
import com.example.rssfeed.data.network.ApiService
import com.example.rssfeed.data.network.NetworkResult
import com.example.rssfeed.data.network.XmlNetworkHandler
import com.example.rssfeed.utils.XML_BASE_URL
import org.xmlpull.v1.XmlPullParserException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
    private val xmlNetworkHandler: XmlNetworkHandler
) : BaseRemoteDataSource() {

    suspend fun getJsonNewsListFromNetwork(page: Int): NetworkResult<News> = safeApiCall {
        jsonNewsListRequestBuilder(page)
    }

    suspend fun getXmlNewsFromNetwork(): NetworkResult<List<XmlArticle>> =
        xmlNewsListRequestBuilder()

    private suspend fun jsonNewsListRequestBuilder(page: Int = 1): Response<News> {
        return apiService.getJsonNewsList(
            domains = "wired.com",
            apiKey = "6b893964ebd740feafd48eb69e42edd1",
            page = page
        )
    }

    private suspend fun xmlNewsListRequestBuilder(): NetworkResult<List<XmlArticle>> =
        try {
            NetworkResult.Success(xmlNetworkHandler.getXmNewsFromNetwork(XML_BASE_URL))
        } catch (e: IOException) {
            NetworkResult.Error(errorMessage = e.message ?: "Something went wrong")
        } catch (e: XmlPullParserException) {
            NetworkResult.Error(errorMessage = e.message ?: "Something went wrong")
        }
}