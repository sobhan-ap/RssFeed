package com.example.rssfeed.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.rssfeed.R
import com.fondesa.recyclerviewdivider.DividerBuilder
import com.fondesa.recyclerviewdivider.dividerBuilder

object DividerItemDecoration {

    fun verticalDivider(context: Context): DividerBuilder {
        val builder = context.dividerBuilder()
        ContextCompat.getDrawable(context, R.drawable.divider_vertical)?.let {
            builder.drawable(it)
        }
        return builder
    }

    fun verticalDividerSpacing16(context: Context): DividerBuilder {
        val builder = context.dividerBuilder()
        ContextCompat.getDrawable(context, R.drawable.divider_vertical_spacing_16)?.let {
            builder.drawable(it)
        }
        return builder
    }

}