package com.example.rssfeed.data.repositories

import com.example.rssfeed.data.model.News
import com.example.rssfeed.data.model.XmlArticle
import com.example.rssfeed.data.network.NetworkResult
import com.example.rssfeed.datasources.LocalDataSource
import com.example.rssfeed.datasources.RemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    suspend fun getBitcoinNewsRemote(page: Int): NetworkResult<News> {
        return when (val result = remoteDataSource.getJsonNewsListFromNetwork(page)) {
            is NetworkResult.Success ->
                try {
                    NetworkResult.Success(
                        data = result.data!!
                    )
                } catch (e: Exception) {
                    NetworkResult.Error(e.message ?: "UnhandledException")
                }
            is NetworkResult.Error ->
                NetworkResult.Error(result.message ?: "UnhandledException")
            else -> {
                NetworkResult.Error("NetworkError")
            }
        }
    }

    suspend fun getXmlNewsRemote(): NetworkResult<List<XmlArticle>> =
        remoteDataSource.getXmlNewsFromNetwork()

}