package com.example.rssfeed.data.network

import com.example.rssfeed.data.model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    suspend fun getBitCoinNewsList(
        @Query(value = "q") query: String,
        @Query(value = "apiKey") apiKey: String,
        @Query(value = "pageSize") pageSize: Int,
        @Query(value = "page") page: Int,
    ): Response<News>
}