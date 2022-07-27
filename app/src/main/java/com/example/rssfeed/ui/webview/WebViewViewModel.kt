package com.example.rssfeed.ui.webview

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
class WebViewViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun setFavoriteState(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            when (article) {
                is JsonArticle -> repository.setFavoriteStateJsonArticle(article)
                is XmlArticle -> repository.setFavoriteStateXmlArticle(article)
            }
        }
    }

}