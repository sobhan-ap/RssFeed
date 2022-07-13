package com.example.rssfeed.data.model

import com.google.gson.annotations.SerializedName

data class JsonArticle(
    @SerializedName("author")
    override val author: String?,
    @SerializedName("description")
    override val description: String?,
    @SerializedName("title")
    override val title: String?,
    @SerializedName("publishedAt")
    override val publishedAt: String?,
    @SerializedName("url")
    override val url: String?,
    @SerializedName("urlToImage")
    override val urlToImage: String?,
    @SerializedName("content")
    val content: String,
    @SerializedName("source")
    val source: Source,
) : Article()
