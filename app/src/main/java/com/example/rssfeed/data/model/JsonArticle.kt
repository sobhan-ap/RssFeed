package com.example.rssfeed.data.model

import com.example.rssfeed.utils.ArticleType
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class JsonArticle (
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
    @Expose(serialize = false, deserialize = false)
    override val viewType: Int = ArticleType.JSON.type,
) : Article()
