package com.example.rssfeed.data.model

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("articles")
    val jsonArticles: List<JsonArticle>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)