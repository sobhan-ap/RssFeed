package com.example.rssfeed.data.network

import com.example.rssfeed.data.model.News
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET
    suspend fun getBitCoinNewsList(

    ): Response<News>
}