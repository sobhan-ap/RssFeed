package com.example.rssfeed.ui.feed.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rssfeed.data.model.JsonArticle
import com.example.rssfeed.data.model.News
import com.example.rssfeed.data.network.NetworkResult
import com.example.rssfeed.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JsonNewsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var _pageNumber = 1

    private val _getJsonNews = MutableLiveData<NetworkResult<News>>()
    val getJsonNews: LiveData<NetworkResult<News>>
        get() = _getJsonNews

    init {
        getJsonNewsList()
    }

    fun getJsonNewsList() {
        viewModelScope.launch(Dispatchers.IO) {
            _getJsonNews.postValue(NetworkResult.Loading())
            _getJsonNews.postValue(
                repository.getJsonNewsRemote(_pageNumber)
            )
        }
    }

    fun unfavoriteArticle(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavoriteJsonArticle(id)
            repository.getAllFavoriteArticles()
        }
    }

    fun setFavoriteArticle(article: JsonArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNewFavoriteJsonArticle(article)
            repository.getAllFavoriteArticles()
        }
    }
}