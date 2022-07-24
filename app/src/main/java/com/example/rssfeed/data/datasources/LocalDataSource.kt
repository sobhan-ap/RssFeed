package com.example.rssfeed.data.datasources

import com.example.rssfeed.data.db.ArticleDao
import com.example.rssfeed.data.model.JsonArticle
import com.example.rssfeed.data.model.XmlArticle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val articleDao: ArticleDao
) {

    suspend fun getAllFavoriteXmlArticles(): Flow<List<XmlArticle>> =
        articleDao.getXmlArticles()

    suspend fun getAllFavoriteJsonArticles(): Flow<List<JsonArticle>> =
        articleDao.getJsonArticles()

    suspend fun insertNewFavoriteXmlArticle(xmlArticle: XmlArticle) {
        articleDao.insertXmlArticle(xmlArticle)
    }

    suspend fun insertNewFavoriteJsonArticle(jsonArticle: JsonArticle) {
        articleDao.insertJsonArticle(jsonArticle)
    }

    suspend fun deleteFavoriteXmlArticle(id: Int) {
        articleDao.deleteXmlArticleById(id)
    }

    suspend fun deleteFavoriteJsonArticle(id: Int) {
        articleDao.deleteJsonArticleById(id)
    }
}