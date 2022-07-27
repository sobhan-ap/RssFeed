package com.example.rssfeed.utils

sealed class GetData() {
    object Remote : GetData()
    object Local : GetData()
}
