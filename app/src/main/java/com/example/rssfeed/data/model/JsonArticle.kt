package com.example.rssfeed.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rssfeed.utils.ArticleType
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "json_article")
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
    @Embedded
    val source: Source,
    @Expose(serialize = false, deserialize = false)
    override val viewType: Int = ArticleType.JSON.type,
    @Expose(serialize = false, deserialize = false)
    override val time: Long = 0L,
    @Expose(serialize = false, deserialize = false)
    override var isFavorite: Boolean,
    @Expose(serialize = false, deserialize = false)
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
) : Article()
