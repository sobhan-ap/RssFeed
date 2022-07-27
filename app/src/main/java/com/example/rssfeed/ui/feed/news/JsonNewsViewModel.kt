package com.example.rssfeed.ui.feed.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rssfeed.data.model.JsonArticle
import com.example.rssfeed.data.network.NetworkResult
import com.example.rssfeed.data.repositories.Repository
import com.example.rssfeed.utils.GetData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JsonNewsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _getJsonNews = MutableLiveData<NetworkResult<List<JsonArticle>>>()
    val getJsonNews: LiveData<NetworkResult<List<JsonArticle>>>
        get() = _getJsonNews

    init {
        getJsonNewsList(GetData.Remote)
    }

    fun getJsonNewsList(getData: GetData) {
        viewModelScope.launch(Dispatchers.IO) {
            _getJsonNews.postValue(NetworkResult.Loading())
            repository.getJsonArticlesList(getData).collect {
                _getJsonNews.postValue(NetworkResult.Success(it))
            }
        }
    }

    fun unfavoriteArticle(article: JsonArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setFavoriteStateJsonArticle(article)
        }
    }

    fun setFavoriteArticle(article: JsonArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setFavoriteStateJsonArticle(article)
        }
    }
}