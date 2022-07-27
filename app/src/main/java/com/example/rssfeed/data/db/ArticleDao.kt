package com.example.rssfeed.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rssfeed.data.model.JsonArticle
import com.example.rssfeed.data.model.XmlArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM xml_article")
    fun getXmlArticleList(): Flow<List<XmlArticle>>

    @Query("SELECT * FROM json_article")
    fun getJsonArticleList(): Flow<List<JsonArticle>>

    @Query("SELECT * FROM xml_article WHERE isFavorite = 1")
    fun getFavoriteXmlArticles(): Flow<List<XmlArticle>>

    @Query("SELECT * FROM json_article WHERE isFavorite = 1")
    fun getFavoriteJsonArticles(): Flow<List<JsonArticle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJsonArticleList(articles: List<JsonArticle>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertXmlArticlesList(list: List<XmlArticle>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateXmlArticle(xmlArticle: XmlArticle): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateJsonArticle(jsonArticle: JsonArticle): Long

    @Query("DELETE FROM xml_article")
    suspend fun clearXmlTable()

    @Query("DELETE FROM json_article")
    suspend fun clearJsonTable()

}