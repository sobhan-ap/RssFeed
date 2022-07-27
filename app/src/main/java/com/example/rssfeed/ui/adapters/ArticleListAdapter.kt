package com.example.rssfeed.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.rssfeed.data.model.Article
import com.example.rssfeed.data.model.JsonArticle
import com.example.rssfeed.data.model.XmlArticle
import com.example.rssfeed.databinding.ItemJsonNewsBinding
import com.example.rssfeed.databinding.ItemXmlNewsBinding
import com.example.rssfeed.utils.ArticleType
import com.example.rssfeed.utils.BaseViewHolder
import com.example.rssfeed.utils.OnArticleClick
import com.example.rssfeed.utils.changeFavoriteState

class ArticleListAdapter(
    private val onArticleItemClick: OnArticleClick,
    private val onFavoriteClick: OnArticleClick
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

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is JsonArticle -> ArticleType.JSON.type
            is XmlArticle -> ArticleType.XML.type
            else -> throw IllegalArgumentException("GetItemType: Unsupported Type")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (viewType) {
            ArticleType.JSON.type -> {
                JsonNewsViewHolder(
                    ItemJsonNewsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ArticleType.XML.type -> {
                XmlNewsViewHolder(
                    ItemXmlNewsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalArgumentException("Unsupported Type: onCreateVH")
        }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        var item: Article = getItem(position)
        var type: Int = getItemViewType(position)
        when (item) {
            is JsonArticle -> (holder as JsonNewsViewHolder).bind(item)
            is XmlArticle -> (holder as XmlNewsViewHolder).bind(item)
            else -> throw IllegalArgumentException("Unsupported Type: onBindVH")
        }

    }

    private fun onFavoriteClickListener(item: Article, img: AppCompatImageView) {
        onFavoriteClick(item)
        img.changeFavoriteState(item.isFavorite)
        item.isFavorite = !item.isFavorite
    }

    inner class JsonNewsViewHolder(private val binding: ItemJsonNewsBinding) :
        BaseViewHolder(binding) {

        override fun bind(item: Article) {
            binding.article = item as JsonArticle
            binding.tvDescription.setOnClickListener {
                onArticleItemClick(item)
            }
            binding.imgFavorite.setOnClickListener {
                onFavoriteClickListener(item, binding.imgFavorite)
            }
        }
    }

    inner class XmlNewsViewHolder(private val binding: ItemXmlNewsBinding) :
        BaseViewHolder(binding) {

        override fun bind(item: Article) {
            binding.xmlArticle = item as XmlArticle
            binding.tvDescription.setOnClickListener {
                onArticleItemClick(item)
            }
            binding.imgFavorite.setOnClickListener {
                onFavoriteClickListener(item, binding.imgFavorite)
            }
        }
    }

}