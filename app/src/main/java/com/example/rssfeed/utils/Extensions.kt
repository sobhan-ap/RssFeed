package com.example.rssfeed.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.rssfeed.R

fun AppCompatImageView.loadImageWithGlide(url: String?) {

    Glide.with(this)
        .load(url)
        .apply {
            RequestOptions()
                .placeholder(R.drawable.ic_not_image)
        }
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun AppCompatImageView.loadImageWithGlideMediumRadius(url: String?) {
    Glide.with(this)
        .load(url)
        .apply(GlideExtensions.mediumRadius(context))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun Activity.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Activity.showLongToast(resourceId: Int) {
    Toast.makeText(this, resourceId, Toast.LENGTH_LONG).show()
}

fun Activity.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.showShortToast(resourceId: Int) {
    Toast.makeText(this, resourceId, Toast.LENGTH_SHORT).show()
}

fun RecyclerView.addVerticalDividerSpacing16(
    context: Context,
    showFirstDivider: Boolean = false,
    showLastDivider: Boolean = true
) {
    if (itemDecorationCount == 0) {

        val divider = DividerItemDecoration.verticalDividerSpacing16(context)

        if (showFirstDivider)
            divider.showFirstDivider()

        if (showLastDivider)
            divider.showLastDivider()

        divider.build().addTo(this)
    }
}

fun NestedScrollView.scrollToTop() {
    smoothScrollTo(0, 0)
}

fun RecyclerView.scrollToTop() {
    smoothScrollToPosition(0)
}

fun AppCompatImageView.changeFavoriteState(state: Boolean) {
    if (state)
        setImageResource(R.drawable.ic_unfavorite_alpha_75)
    else
        setImageResource(R.drawable.ic_favorite_alpha_75)
}

