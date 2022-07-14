package com.example.rssfeed.data.model

import com.example.rssfeed.utils.ArticleType


data class XmlArticle(
    override val author: String?,
    override val description: String?,
    override val title: String?,
    override val publishedAt: String?,
    override val url: String?,
    override val urlToImage: String?,
    override val viewType: Int = ArticleType.XML.type,
) : Article()