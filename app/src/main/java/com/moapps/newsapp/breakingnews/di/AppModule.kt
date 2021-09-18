package com.moapps.newsapp.breakingnews.di

import android.content.Context
import androidx.room.Room
import com.moapps.newsapp.breakingnews.data.local.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import androidx.sqlite.db.SupportSQLiteDatabase

import androidx.room.migration.Migration




@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(appContext, AppDatabase::class.java, "appDatabase")
            .allowMainThreadQueries()
            .build()


    @Provides
    fun provideArticlesDao(db: AppDatabase): ArticlesDao = db.articlesDao()

    @Provides
    fun provideSitesDao(db: AppDatabase): SitesDao = db.sitesDao()

    @Provides
    fun provideInterestsDao(db: AppDatabase): InterestsDao = db.interestsDao()

    @Provides
    fun provideArticlesBookmarkDao(db: AppDatabase): BookmarkArticlesDao = db.articlesBookmarkDao()

    @Provides
    fun provideBreakingNewsDao(db: AppDatabase): BreakingNewsDao = db.breakingNewsDao()

}