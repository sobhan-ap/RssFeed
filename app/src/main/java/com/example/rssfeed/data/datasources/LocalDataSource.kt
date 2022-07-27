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

    fun getXmlArticleList(): Flow<List<XmlArticle>> =
        articleDao.getXmlArticleList()

    fun getJsonArticleList(): Flow<List<JsonArticle>> =
        articleDao.getJsonArticleList()

    fun getAllFavoriteXmlArticles(): Flow<List<XmlArticle>> =
        articleDao.getFavoriteXmlArticles()

    fun getAllFavoriteJsonArticles(): Flow<List<JsonArticle>> =
        articleDao.getFavoriteJsonArticles()

    suspend fun insertJsonArticleList(articles: List<JsonArticle>) {
        articleDao.insertJsonArticleList(articles)
    }

    suspend fun insertXmlArticleList(articles: List<XmlArticle>) {
        articleDao.insertXmlArticlesList(articles)
    }

    suspend fun setFavoriteStateXmlArticle(xmlArticle: XmlArticle): Long =
        articleDao.insertOrUpdateXmlArticle(xmlArticle)

    suspend fun setFavoriteStateJsonArticle(jsonArticle: JsonArticle): Long =
        articleDao.insertOrUpdateJsonArticle(jsonArticle)

    suspend fun clearXmlTableUntilFavorites() {
        articleDao.clearXmlTableUntilFavorites()
    }

    suspend fun clearJsonTableUntilFavorites() {
        articleDao.clearJsonTableUntilFavorites()
    }
}