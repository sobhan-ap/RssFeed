package com.example.rssfeed.utils

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.rssfeed.data.model.Article

abstract class BaseViewHolder(bindingParam: ViewDataBinding) :
    RecyclerView.ViewHolder(bindingParam.root) {
    abstract fun bind(item: Article)
}