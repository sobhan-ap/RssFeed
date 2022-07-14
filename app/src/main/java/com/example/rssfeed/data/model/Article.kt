package com.example.rssfeed.data.model

abstract class Article {
    abstract val author: String?
    abstract val description: String?
    abstract val title: String?
    abstract val publishedAt: String?
    abstract val url: String?
    abstract val urlToImage: String?
    abstract val viewType: Int
}