package com.example.rssfeed.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rssfeed.data.model.JsonArticle
import com.example.rssfeed.data.model.XmlArticle

@Database(
    entities = [JsonArticle::class, XmlArticle::class],
    version = 1, exportSchema = false
)
abstract class ArticleDataBase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

}