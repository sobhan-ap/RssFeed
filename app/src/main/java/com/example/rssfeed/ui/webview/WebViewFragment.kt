package com.example.rssfeed.ui.webview

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.rssfeed.R
import com.example.rssfeed.data.model.Article
import com.example.rssfeed.databinding.FragmentWebViewBinding
import com.example.rssfeed.utils.BaseFragment
import com.example.rssfeed.utils.WEB_URL_ARG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : BaseFragment<FragmentWebViewBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_web_view
    private val _viewModel by viewModels<WebViewViewModel>()

    private var article: Article? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            article = bundle.getParcelable(WEB_URL_ARG)!!
        }
        initViews()
        binding.article = article!!
    }

    private fun initViews() {
        article?.let {
            initWebView(it.url ?: "")
        }
        binding.btnFavorite.setOnClickListener {
            if (article!!.isFavorite)
                binding.btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_unfavorite_alpha_75)
                ) else
                binding.btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_alpha_75)
                )

            article?.let {
                _viewModel.setFavoriteState(it.apply {
                    isFavorite = !isFavorite
                })
            }

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