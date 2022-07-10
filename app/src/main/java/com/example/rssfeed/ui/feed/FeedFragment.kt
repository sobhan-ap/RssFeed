package com.example.rssfeed.ui.feed

import android.os.Bundle
import android.view.View
import com.example.rssfeed.R
import com.example.rssfeed.databinding.FragmentFeedBinding
import com.example.rssfeed.ui.adapters.FeedTabsAdapter
import com.example.rssfeed.utils.BaseFragment
import com.example.rssfeed.utils.FEED_TABS_NUMBER
import com.example.rssfeed.utils.FeedTabsType
import com.google.android.material.tabs.TabLayoutMediator

class FeedFragment : BaseFragment<FragmentFeedBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_feed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        initTabsAndViewPager()
    }

    private fun initTabsAndViewPager() {
        binding.vpFeed.adapter = FeedTabsAdapter(this, FEED_TABS_NUMBER)
        TabLayoutMediator(
            binding.tabsFeed,
            binding.vpFeed,
        ) { tab, position ->
            with(tab) {
                text = FeedTabsType.values()[position].title
            }
        }.attach()
    }

}
