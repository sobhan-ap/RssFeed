package com.example.rssfeed.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rssfeed.R
import com.example.rssfeed.databinding.FragmentFavoriteBinding
import com.example.rssfeed.ui.adapters.ArticleListAdapter
import com.example.rssfeed.utils.BaseFragment
import com.example.rssfeed.utils.WEB_URL_ARG
import com.example.rssfeed.utils.addVerticalDividerSpacing16
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_favorite

    private val _viewModel by viewModels<FavoriteViewModel>()
    private lateinit var _articleAdapter: ArticleListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        _viewModel.articlesList.observe(viewLifecycleOwner) { articles ->
            if (binding.refreshLayout.isRefreshing)
                binding.refreshLayout.isRefreshing = false
            _articleAdapter.submitList(articles)
        }
    }

    private fun initViews() {
        initRecyclerView()
        binding.refreshLayout.setOnRefreshListener {
            _viewModel.getAllArticles()
        }
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
                _viewModel.unfavoriteArticle(article.apply {
                    isFavorite = !isFavorite
                })
            })

        binding.rvFavoriteList.apply {
            addVerticalDividerSpacing16(requireContext())
            adapter = _articleAdapter
        }
    }


}