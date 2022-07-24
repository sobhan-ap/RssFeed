package com.example.rssfeed.ui.feed.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rssfeed.data.model.XmlArticle
import com.example.rssfeed.data.network.NetworkResult
import com.example.rssfeed.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class XmlNewsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _getXmlNews = MutableLiveData<NetworkResult<List<XmlArticle>>>()
    val getXmlNews: LiveData<NetworkResult<List<XmlArticle>>>
        get() = _getXmlNews

    init {
        getXmlNewsList()
    }

    fun getXmlNewsList() {
        viewModelScope.launch(Dispatchers.IO) {
            _getXmlNews.postValue(NetworkResult.Loading())
            _getXmlNews.postValue(
                repository.getXmlNewsRemote()
            )
        }
    }

    fun unfavoriteFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavoriteXmlArticle(id)
            repository.getAllFavoriteArticles()
        }
    }

    fun setFavoriteArticle(article: XmlArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNewFavoriteXmlArticle(article)
            repository.getAllFavoriteArticles()
        }
    }
}