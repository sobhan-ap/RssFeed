package com.example.rssfeed.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rssfeed.data.model.JsonArticle
import com.example.rssfeed.databinding.ItemNewsOneBinding

class ArticleListAdapter(
    private val onArticleItemClick: (JsonArticle) -> Unit
) : ListAdapter<JsonArticle, ArticleListAdapter.NewsListViewHolder>(
    object : DiffUtil.ItemCallback<JsonArticle>() {
        override fun areItemsTheSame(oldItem: JsonArticle, newItem: JsonArticle): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: JsonArticle, newItem: JsonArticle): Boolean =
            oldItem == newItem
    }
) {

    inner class NewsListViewHolder(private val binding: ItemNewsOneBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: JsonArticle) {
            binding.article = item
            onArticleItemClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        return NewsListViewHolder(
            ItemNewsOneBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}