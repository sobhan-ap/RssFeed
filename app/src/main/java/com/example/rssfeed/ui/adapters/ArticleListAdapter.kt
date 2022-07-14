package com.example.rssfeed.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.rssfeed.data.model.Article
import com.example.rssfeed.data.model.JsonArticle
import com.example.rssfeed.data.model.XmlArticle
import com.example.rssfeed.databinding.ItemJsonNewsBinding
import com.example.rssfeed.databinding.ItemXmlNewsBinding
import com.example.rssfeed.utils.ArticleType
import com.example.rssfeed.utils.BaseViewHolder

class ArticleListAdapter(
    private val onArticleItemClick: (Article) -> Unit
) : ListAdapter<Article, BaseViewHolder>(

    object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            when (oldItem) {
                is JsonArticle -> oldItem as JsonArticle == newItem as JsonArticle
                is XmlArticle -> oldItem as XmlArticle == newItem as XmlArticle
                else -> false
            }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        if (viewType == ArticleType.XML.type)
            XmlNewsViewHolder(
                ItemXmlNewsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            ) else
                JsonNewsViewHolder(
            ItemJsonNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) =
        holder.bind(getItem(position))


    inner class JsonNewsViewHolder(private val binding: ItemJsonNewsBinding) :
        BaseViewHolder(binding) {

        override fun bind(item: Article) {
            binding.article = item as JsonArticle
            onArticleItemClick(item)
        }
    }

    inner class XmlNewsViewHolder(private val binding: ItemXmlNewsBinding) :
        BaseViewHolder(binding) {

        override fun bind(item: Article) {
            binding.xmlArticle = item as XmlArticle
            onArticleItemClick(item)
        }
    }

}