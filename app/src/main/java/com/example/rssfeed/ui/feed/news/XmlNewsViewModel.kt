package com.example.rssfeed.ui.feed.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rssfeed.data.model.XmlArticle
import com.example.rssfeed.data.network.NetworkResult
import com.example.rssfeed.data.repositories.Repository
import com.example.rssfeed.utils.GetData
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
        getXmlNewsList(GetData.Remote)
    }

    fun getXmlNewsList(getData: GetData) {
        viewModelScope.launch(Dispatchers.IO) {
            _getXmlNews.postValue(NetworkResult.Loading())
            repository.getXmlArticlesList(getData).collect { articles ->
                _getXmlNews.postValue(
                    NetworkResult.Success(articles)
                )
            }
        }
    }

    fun unfavoriteFavorite(article: XmlArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setFavoriteStateXmlArticle(article)
        }
    }

    fun setFavoriteArticle(article: XmlArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setFavoriteStateXmlArticle(article)
        }
    }
}