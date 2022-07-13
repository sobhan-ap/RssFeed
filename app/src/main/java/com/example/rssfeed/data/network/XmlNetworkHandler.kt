package com.example.rssfeed.data.network

import com.example.rssfeed.data.model.XmlArticle
import com.example.rssfeed.utils.NewsXmlParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject


class XmlNetworkHandler @Inject constructor(
    private val newsXmlParser: NewsXmlParser
) {

    @Throws(XmlPullParserException::class, IOException::class)
    suspend fun getXmNewsFromNetwork(urlString: String): List<XmlArticle> =
        downloadUrl(urlString)?.use { stream ->
            newsXmlParser.parse(stream)
        } ?: emptyList()

    @Throws(IOException::class)
    private fun downloadUrl(urlString: String): InputStream? {
        val url = URL(urlString)
        return (url.openConnection() as? HttpURLConnection)?.run {
            readTimeout = 10000
            connectTimeout = 15000
            requestMethod = "GET"
            doInput = true
            connect()
            inputStream
        }
    }
}