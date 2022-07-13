package com.example.rssfeed.ui.feed.news

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.rssfeed.R
import com.example.rssfeed.data.network.NetworkResult
import com.example.rssfeed.databinding.FragmentJsonNewsBinding
import com.example.rssfeed.ui.adapters.ArticleListAdapter
import com.example.rssfeed.ui.feed.FeedViewModel
import com.example.rssfeed.utils.BaseFragment
import com.example.rssfeed.utils.addVerticalDividerSpacing16
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JsonNewsFragment : BaseFragment<FragmentJsonNewsBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_json_news

    private val _viewModel by viewModels<FeedViewModel>()
    private lateinit var _articleAdapter: ArticleListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }


    private fun initObservers() {
        _viewModel.getBitcoinNews.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    binding.refreshLayout.isRefreshing = false
                    _articleAdapter.submitList(result.data?.jsonArticles)
                }
                is NetworkResult.Loading -> {
                    binding.refreshLayout.isRefreshing = true
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initViews() {
        binding.refreshLayout.setOnRefreshListener {
            _viewModel.getBitcoinNewsList()
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        _articleAdapter = ArticleListAdapter() {
            //TODO navigation to WebView
        }
        binding.rvBitcoinNewsList.apply {
            addVerticalDividerSpacing16(requireContext())
            adapter = _articleAdapter
        }
    }


}