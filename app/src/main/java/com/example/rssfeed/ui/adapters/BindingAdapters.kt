package com.example.rssfeed.ui.adapters

import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.example.rssfeed.R
import com.example.rssfeed.utils.loadImageWithGlideMediumRadius
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("setImageUrlMediumRadius")
fun setImageUrlMediumRadius(imageView: AppCompatImageView, url: String?) {
    imageView.loadImageWithGlideMediumRadius(url)
}

@BindingAdapter("setBackground")
fun setBackground(view: View, background: Int) {
    if (background != 0) {
        val drawable = AppCompatResources.getDrawable(view.context, background)
        view.background = drawable
    }
}

@BindingAdapter("setVisibilityGone")
fun setVisibilityGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) View.GONE else View.VISIBLE
}

@BindingAdapter("setVisibilityInvisible")
fun setVisibilityInvisible(view: View, isInvisible: Boolean) {
    view.visibility = if (isInvisible) View.INVISIBLE else View.VISIBLE
}

@BindingAdapter("setDisable")
fun setDisable(view: View, isLoading: Boolean) {
    view.isEnabled = !isLoading
}

@BindingAdapter("setImageFavoriteState")
fun setImageFavoriteState(imageView: AppCompatImageView, isFavorite: Boolean = false) {
    if (isFavorite)
        imageView.setImageResource(R.drawable.ic_favorite_alpha_75)
    else
        imageView.setImageResource(R.drawable.ic_unfavorite_alpha_75)
}

@BindingAdapter("setFabFavoriteState")
fun setFabFavoriteState(fab: FloatingActionButton, isFavorite: Boolean) {
    if (isFavorite)
        fab.setImageResource(R.drawable.ic_favorite_alpha_75)
    else
        fab.setImageResource(R.drawable.ic_unfavorite_alpha_75)
}