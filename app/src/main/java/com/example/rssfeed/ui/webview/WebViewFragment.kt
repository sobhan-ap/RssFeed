package com.example.rssfeed.ui.webview

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.rssfeed.R
import com.example.rssfeed.data.model.Article
import com.example.rssfeed.databinding.FragmentWebViewBinding
import com.example.rssfeed.utils.BaseFragment
import com.example.rssfeed.utils.WEB_URL_ARG


class WebViewFragment : BaseFragment<FragmentWebViewBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_web_view

//    private val args by navArgs<WebViewFragmentArgs>()
    private var article: Article? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            article = bundle.getParcelable(WEB_URL_ARG)
        }

        article?.let { article ->
            initWebView(article.url ?: "")
        }
    }

    private fun initWebView(url: String) {
        binding.webView.webViewClient = WebViewClient()
        binding.webView.apply {
            loadUrl(url)
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true)
        }
    }

}