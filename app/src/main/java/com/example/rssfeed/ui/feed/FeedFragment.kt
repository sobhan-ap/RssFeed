package com.example.rssfeed.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rssfeed.databinding.FragmentFeedBinding
import com.example.rssfeed.ui.adapters.FeedTabsAdapter
import com.example.rssfeed.utils.FEED_TABS_NUMBER
import com.example.rssfeed.utils.FeedTabsType
import com.google.android.material.tabs.TabLayoutMediator

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
