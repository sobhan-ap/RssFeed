package com.example.rssfeed.ui.feed.news

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rssfeed.R
import com.example.rssfeed.data.model.XmlArticle
import com.example.rssfeed.data.network.NetworkResult
import com.example.rssfeed.databinding.FragmentXmlNewsBinding
import com.example.rssfeed.ui.adapters.ArticleListAdapter
import com.example.rssfeed.utils.BaseFragment
import com.example.rssfeed.utils.GetData
import com.example.rssfeed.utils.WEB_URL_ARG
import com.example.rssfeed.utils.addVerticalDividerSpacing16
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class XmlNewsFragment : BaseFragment<FragmentXmlNewsBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_xml_news

    private val _viewModel by viewModels<XmlNewsViewModel>()
    private lateinit var _articleAdapter: ArticleListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    private fun initObservers() {
        _viewModel.getXmlNews.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    binding.refreshLayout.isRefreshing = false
                    _articleAdapter.submitList(result.data)
                }
                is NetworkResult.Loading -> {
                    binding.refreshLayout.isRefreshing = true
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), "XmlNewsError", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initViews() {
        binding.refreshLayout.setOnRefreshListener {
            _viewModel.getXmlNewsList(GetData.Local)
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        _articleAdapter = ArticleListAdapter(
            onArticleItemClick = { article ->
                val bundle = Bundle().apply {
                    putParcelable(WEB_URL_ARG, article)
                }
                findNavController().navigate(R.id.webViewFragment, bundle)
            },
            onFavoriteClick = { article ->
                article as XmlArticle
                if (article.isFavorite)
                    _viewModel.unfavoriteFavorite(article.apply {
                        isFavorite = !isFavorite
                    })
                else
                    _viewModel.setFavoriteArticle(article.apply {
                        isFavorite = !isFavorite
                    })
            })
        binding.rvNewsList.apply {
            addVerticalDividerSpacing16(requireContext())
            adapter = _articleAdapter
        }
    }
}