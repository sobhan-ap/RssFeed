package com.example.rssfeed.utils

import android.content.Context
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.rssfeed.R

object GlideExtensions {

    fun default(): RequestOptions {
        return RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    }

    fun mediumRadius(context: Context): RequestOptions {
        return RequestOptions()
            .transform(
                CenterCrop(),
                RoundedCorners(
                    context.resources.getDimensionPixelSize(R.dimen.radius_corner_6)
                )
            )
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    }

}