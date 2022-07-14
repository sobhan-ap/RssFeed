package com.example.rssfeed.data.datasources

import com.example.rssfeed.data.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRemoteDataSource {

    protected suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): NetworkResult<T> {

        return withContext(Dispatchers.Default) {
            try {
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    NetworkResult.Success(data = response.body()!!)
                } else {
                    NetworkResult.Error(
                        errorMessage = response.errorBody().toString()
                    )
                }
            } catch (e: HttpException) {
                NetworkResult.Error(errorMessage = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                NetworkResult.Error("Please check your network connection")
            } catch (e: Exception) {
                NetworkResult.Error(errorMessage = "Something went wrong")
            }
        }
    }

}
