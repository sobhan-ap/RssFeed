package com.example.rssfeed.data.repositories

import com.example.rssfeed.data.datasources.LocalDataSource
import com.example.rssfeed.data.datasources.RemoteDataSource
import com.example.rssfeed.data.model.Article
import com.example.rssfeed.data.model.JsonArticle
import com.example.rssfeed.data.model.News
import com.example.rssfeed.data.model.XmlArticle
import com.example.rssfeed.data.network.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    private lateinit var favoriteJsonArticles: Map<String, Int>
    private lateinit var favoriteXmlArticles: Map<String, Int>

    init {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.getAllFavoriteJsonArticles().collect { localArticles ->
                favoriteJsonArticles = localArticles.associate {
                    it.title!! to it.id
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.getAllFavoriteXmlArticles().collect { localArticles ->
                favoriteXmlArticles = localArticles.associate {
                    it.title!! to it.id
                }
            }
        }
    }

    suspend fun getJsonNewsRemote(page: Int): NetworkResult<News> {
        return when (val result = remoteDataSource.getJsonNewsListFromNetwork(page)) {
            is NetworkResult.Success ->
                try {
                    result.data!!.jsonArticles.forEach {
                        if (favoriteJsonArticles.containsKey(it.title)) {
                            it.isFavorite = true
                            it.id = favoriteJsonArticles[it.title]!!
                        }
                    }
                    NetworkResult.Success(
                        data = result.data
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

    suspend fun getXmlNewsRemote(): NetworkResult<List<XmlArticle>> {
        return try {
            val result: List<XmlArticle> = remoteDataSource.getXmlNewsFromNetwork()
            result.forEach {
                if (favoriteXmlArticles.containsKey(it.title)) {
                    it.isFavorite = true
                    it.id = favoriteXmlArticles[it.title]!!
                }
            }
            NetworkResult.Success(result)
        } catch (e: IOException) {
            NetworkResult.Error(errorMessage = e.message.toString())
        } catch (e: XmlPullParserException) {
            NetworkResult.Error(errorMessage = e.message.toString())
        }
    }

    suspend fun getAllFavoriteArticles(): Flow<List<Article>> =
        localDataSource.getAllFavoriteXmlArticles()
            .zip(localDataSource.getAllFavoriteJsonArticles()) { xml, json ->
                val lst = mutableListOf<Article>()
                with(lst) {
                    addAll(xml)
                    addAll(json)
                    return@zip lst
                }
            }


    suspend fun insertNewFavoriteXmlArticle(xmlArticle: XmlArticle) {
        if (!favoriteXmlArticles.containsKey(xmlArticle.title))
            localDataSource.insertNewFavoriteXmlArticle(
                xmlArticle.copy(
                    isFavorite = true,
                    time = getCurrentTimestamp()
                )
            )
        getAllFavoriteArticles()
    }

    suspend fun insertNewFavoriteJsonArticle(jsonArticle: JsonArticle) {
        if (!favoriteJsonArticles.containsKey(jsonArticle.title))
            localDataSource.insertNewFavoriteJsonArticle(
                jsonArticle.copy(
                    isFavorite = true,
                    time = getCurrentTimestamp()
                )
            )
    }

    suspend fun deleteFavoriteXmlArticle(id: Int) {
        localDataSource.deleteFavoriteXmlArticle(id)
    }

    suspend fun deleteFavoriteJsonArticle(id: Int) {
        localDataSource.deleteFavoriteJsonArticle(id)
    }

    private fun getCurrentTimestamp(): Long = System.currentTimeMillis()
}