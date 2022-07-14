package com.example.rssfeed.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rssfeed.data.model.News
import com.example.rssfeed.data.model.XmlArticle
import com.example.rssfeed.data.network.NetworkResult
import com.example.rssfeed.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var _pageNumber = 1

    private val _getJsonNews = MutableLiveData<NetworkResult<News>>()
    val getJsonNews: LiveData<NetworkResult<News>>
        get() = _getJsonNews

    private val _getXmlNews = MutableLiveData<NetworkResult<List<XmlArticle>>>()
    val getXmlNews: LiveData<NetworkResult<List<XmlArticle>>>
        get() = _getXmlNews

    init {
        getJsonNewsList()
        getXmlNewsList()
    }

    fun getJsonNewsList() {
        viewModelScope.launch(Dispatchers.IO) {
            _getJsonNews.postValue(NetworkResult.Loading())
            _getJsonNews.postValue(
                repository.getJsonNewsRemote(_pageNumber)
            )
        }
    }

    fun getXmlNewsList() {
        viewModelScope.launch(Dispatchers.IO) {
            _getXmlNews.postValue(NetworkResult.Loading())
            _getXmlNews.postValue(
                repository.getXmlNewsRemote()
            )
        }
    }
}