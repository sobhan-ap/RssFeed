package com.example.rssfeed.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rssfeed.data.model.Article
import com.example.rssfeed.data.model.JsonArticle
import com.example.rssfeed.data.model.XmlArticle
import com.example.rssfeed.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _articlesList = MutableLiveData<List<Article>>()
    val articlesList
        get() = _articlesList

    init {
        getAllArticles()
    }

    fun getAllArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFavoriteArticles().collect { articles ->
                _articlesList.postValue(
                    articles.sortedByDescending {
                        it.time
                    }
                )
            }
        }
    }

    fun unfavoriteArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            when (article) {
                is JsonArticle -> repository.setFavoriteStateJsonArticle(article)
                is XmlArticle -> repository.setFavoriteStateXmlArticle(article)
            }
            getAllArticles()
        }
    }

}