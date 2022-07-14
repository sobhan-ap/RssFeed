package com.example.rssfeed.utils

import android.util.Xml
import com.example.rssfeed.data.model.XmlArticle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class XmlNewsParser @Inject constructor() {

    private val nameSpace: String? = null

    @Throws(XmlPullParserException::class, IOException::class)
    suspend fun parse(inputStream: InputStream): List<XmlArticle> =
        withContext(Dispatchers.Default) {
            inputStream.use {
                val parser: XmlPullParser = Xml.newPullParser()
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
                parser.setInput(it, null)
                parser.nextTag()
                readFeed(parser)
            }
        }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFeed(parser: XmlPullParser): List<XmlArticle> {
        val news = mutableListOf<XmlArticle>()

        parser.require(XmlPullParser.START_TAG, nameSpace, "rss")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            parser.require(XmlPullParser.START_TAG, nameSpace, "channel")
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.eventType != XmlPullParser.START_TAG) {
                    continue
                }
                if (parser.name == "item") {
                    news.add(readNews(parser))
                } else {
                    skip(parser)
                }
            }
        }
        return news
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readNews(parser: XmlPullParser): XmlArticle {
        parser.require(XmlPullParser.START_TAG, nameSpace, "item")

        var title: String? = null
        var link: String? = null
        var description: String? = null
        var creator: String? = null
        var pubDate: String? = null
        var imageUrl: String? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "title" -> title = readTags(parser, "title")
                "link" -> link = readTags(parser, "link")
                "description" -> description = readTags(parser, "description")
                "dc:creator" -> creator = readTags(parser, "dc:creator")
                "pubDate" -> pubDate = readTags(parser, "pubDate")
                "media:content" -> imageUrl = readImage(parser)
                else -> skip(parser)
            }
        }
        return XmlArticle(
            author = creator,
            description = description,
            title = title,
            publishedAt = pubDate,
            url = link,
            urlToImage = imageUrl
        )
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readTags(parser: XmlPullParser, tagStr: String): String {
        parser.require(XmlPullParser.START_TAG, nameSpace, tagStr)
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, nameSpace, tagStr)
        return title
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }


    @Throws(IOException::class, XmlPullParserException::class)
    private fun readImage(parser: XmlPullParser): String {
        var link = ""
        parser.require(XmlPullParser.START_TAG, nameSpace, "media:content")
        val tag = parser.name
        val mediumType = parser.getAttributeValue(null, "medium")
        if (tag == "media:content") {
            if (mediumType == "image") {
                link = parser.getAttributeValue(null, "url")
                parser.nextTag()
            }
        }
        parser.require(XmlPullParser.END_TAG, nameSpace, "media:content")
        return link
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}