package com.example.rssfeed.data.repositories

import com.example.rssfeed.data.datasources.LocalDataSource
import com.example.rssfeed.data.datasources.RemoteDataSource
import com.example.rssfeed.data.model.Article
import com.example.rssfeed.data.model.JsonArticle
import com.example.rssfeed.data.model.XmlArticle
import com.example.rssfeed.data.network.NetworkResult
import com.example.rssfeed.utils.GetData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    private suspend fun getJsonNewsRemote(page: Int): NetworkResult<Unit> {
        return when (val result = remoteDataSource.getJsonNewsListFromNetwork(page)) {
            is NetworkResult.Success ->
                try {
                    NetworkResult.Success(
                        data = localDataSource.insertJsonArticleList(result.data!!.jsonArticles)
                    )
                } catch (e: Exception) {
                    NetworkResult.Error(e.message.toString())
                }
            is NetworkResult.Error ->
                NetworkResult.Error(result.message.toString())
            else -> {
                NetworkResult.Error("NetworkError")
            }
        }
    }

    private suspend fun getXmlNewsRemote(): NetworkResult<Unit> {
        return try {
            NetworkResult.Success(
                localDataSource.insertXmlArticleList(remoteDataSource.getXmlNewsFromNetwork())
            )
        } catch (e: IOException) {
            NetworkResult.Error(errorMessage = e.message.toString())
        } catch (e: XmlPullParserException) {
            NetworkResult.Error(errorMessage = e.message.toString())
        }
    }

    suspend fun getJsonArticlesList(getData: GetData): Flow<List<JsonArticle>> {
        return when (getData) {
            GetData.Remote -> {
                localDataSource.clearJsonTableUntilFavorites()
                getJsonNewsRemote(1)
                localDataSource.getJsonArticleList()
            }
            GetData.Local -> {
                localDataSource.getJsonArticleList()
            }
        }
    }

    suspend fun getXmlArticlesList(getData: GetData): Flow<List<XmlArticle>> =
        when (getData) {
            GetData.Remote -> {
                localDataSource.clearXmlTableUntilFavorites()
                getXmlNewsRemote()
                localDataSource.getXmlArticleList()
            }
            GetData.Local -> localDataSource.getXmlArticleList()
        }

    fun getAllFavoriteArticles(): Flow<List<Article>> =
        localDataSource.getAllFavoriteJsonArticles()
            .zip(localDataSource.getAllFavoriteXmlArticles()) { json, xml ->
                mutableListOf<Article>().apply {
                    addAll(json)
                    addAll(xml)
                }
            }

    suspend fun setFavoriteStateXmlArticle(xmlArticle: XmlArticle): Long =
        localDataSource.setFavoriteStateXmlArticle(
            xmlArticle.copy(
                time = getCurrentTimestamp()
            )
        )

    suspend fun setFavoriteStateJsonArticle(jsonArticle: JsonArticle): Long =
        localDataSource.setFavoriteStateJsonArticle(
            jsonArticle.copy(
                time = getCurrentTimestamp()
            )
        )

    private fun getCurrentTimestamp(): Long = System.currentTimeMillis()
}