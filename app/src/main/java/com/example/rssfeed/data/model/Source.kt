package com.example.rssfeed.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
    @SerializedName("id")
    @ColumnInfo(name="source_id")
    val id: String,
    @SerializedName("name")
    @ColumnInfo(name="source_name")
    val name: String
) : Parcelable
