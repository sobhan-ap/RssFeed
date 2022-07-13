package com.example.rssfeed.data.network

import com.example.rssfeed.data.model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    suspend fun getJsonNewsList(
        @Query(value = "domains") domains: String,
        @Query(value = "apiKey") apiKey: String,
        @Query(value = "page") page: Int,
    ): Response<News>
}