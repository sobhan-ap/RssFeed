package com.example.rssfeed.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.rssfeed.ui.feed.news.JsonNewsFragment
import com.example.rssfeed.ui.feed.news.XmlNewsFragment

class FeedTabsAdapter(fragment: Fragment, private val count: Int) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = count

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> JsonNewsFragment()
            1 -> XmlNewsFragment()
            else -> throw Exception()
        }
    }
}