package com.example.rssfeed.data.model


data class XmlArticle(
    override val author: String?,
    override val description: String?,
    override val title: String?,
    override val publishedAt: String?,
    override val url: String?,
    override val urlToImage: String?,
) : Article()