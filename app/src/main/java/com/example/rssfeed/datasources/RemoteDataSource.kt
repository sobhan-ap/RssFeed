package com.example.rssfeed.datasources

import com.example.rssfeed.data.model.News
import com.example.rssfeed.data.network.ApiService
import com.example.rssfeed.data.network.NetworkResult
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : BaseRemoteDataSource() {

    suspend fun getBitcoinList(page: Int): NetworkResult<News> = safeApiCall {
        requestGetBitcoinList(page)
    }

    private suspend fun requestGetBitcoinList(page: Int = 1): Response<News> {
        return apiService.getBitCoinNewsList(
            query = "bitcoin",
            apiKey = "6b893964ebd740feafd48eb69e42edd1",
            pageSize = 35,
            page = page
        )
    }
}