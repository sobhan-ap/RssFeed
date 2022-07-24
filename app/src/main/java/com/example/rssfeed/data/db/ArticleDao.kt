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
    fun getXmlArticles(): Flow<List<XmlArticle>>

    @Query("SELECT * FROM json_article")
    fun getJsonArticles(): Flow<List<JsonArticle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertXmlArticle(xmlArticle: XmlArticle)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJsonArticle(jsonArticle: JsonArticle)

    @Query("DELETE FROM xml_article WHERE id = :articleId")
    suspend fun deleteXmlArticleById(articleId: Int)

    @Query("DELETE FROM json_article WHERE id = :articleId")
    suspend fun deleteJsonArticleById(articleId: Int)

}