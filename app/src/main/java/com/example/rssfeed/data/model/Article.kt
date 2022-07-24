package com.example.rssfeed.data.model

import android.os.Parcelable

abstract class Article : Parcelable {
    abstract val author: String?
    abstract val description: String?
    abstract val title: String?
    abstract val publishedAt: String?
    abstract val url: String?
    abstract val urlToImage: String?
    abstract val viewType: Int
    abstract val time: Long
    abstract var isFavorite: Boolean
}