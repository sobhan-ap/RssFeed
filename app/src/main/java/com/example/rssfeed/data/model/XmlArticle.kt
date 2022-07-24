package com.example.rssfeed.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rssfeed.utils.ArticleType
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "xml_article")
data class XmlArticle(
    override val author: String?,
    override val description: String?,
    override val title: String?,
    override val publishedAt: String?,
    override val url: String?,
    override val urlToImage: String?,
    override val viewType: Int = ArticleType.XML.type,
    @Expose(serialize = false, deserialize = false)
    override val time: Long = 0L,
    @Expose(serialize = false, deserialize = false)
    override var isFavorite: Boolean = false,
    @Expose(serialize = false, deserialize = false)
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
) : Article()