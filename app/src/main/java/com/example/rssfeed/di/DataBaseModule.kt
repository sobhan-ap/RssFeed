package com.example.rssfeed.di

import android.content.Context
import androidx.room.Room
import com.example.rssfeed.data.db.ArticleDao
import com.example.rssfeed.data.db.ArticleDataBase
import com.example.rssfeed.utils.DATA_BASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun provideArticleDao(articleDataBase: ArticleDataBase): ArticleDao {
        return articleDataBase.articleDao()
    }

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): ArticleDataBase =
        Room.databaseBuilder(
            context,
            ArticleDataBase::class.java,
            DATA_BASE_NAME
        ).build()
}