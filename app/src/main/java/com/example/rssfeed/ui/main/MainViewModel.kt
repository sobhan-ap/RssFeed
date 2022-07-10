package com.example.rssfeed.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rssfeed.data.model.News
import com.example.rssfeed.data.network.NetworkResult
import com.example.rssfeed.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var _pageNumber = 1

    private val _getBitcoinNews = MutableLiveData<NetworkResult<News>>()
    val getBitcoinNews: LiveData<NetworkResult<News>>
        get() = _getBitcoinNews

    init {
        getBitcoinNewsList()
    }

    fun getBitcoinNewsList() {
        viewModelScope.launch(Dispatchers.IO) {
            _getBitcoinNews.postValue(NetworkResult.Loading())
            _getBitcoinNews.postValue(
                repository.getBitcoinNewsRemote(_pageNumber)
            )
        }
    }
}