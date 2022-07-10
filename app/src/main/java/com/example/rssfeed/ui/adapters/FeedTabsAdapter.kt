package com.example.rssfeed.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.rssfeed.ui.feed.news.NewsFragment

class FeedTabsAdapter(fragment: Fragment, private val count: Int) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = count

    override fun createFragment(position: Int): Fragment {
        return NewsFragment()
    }
}