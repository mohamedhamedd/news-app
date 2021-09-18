package com.moapps.newsapp.breakingnews.data.local

import android.annotation.SuppressLint
import androidx.room.Database
import androidx.room.RoomDatabase
import com.moapps.newsapp.breakingnews.data.models.bookmark.BookmarkArticles
import com.moapps.newsapp.breakingnews.data.models.breaking.Breaking
import com.moapps.newsapp.breakingnews.data.models.interests.Interests
import com.moapps.newsapp.breakingnews.data.models.news.Article
import com.moapps.newsapp.breakingnews.data.models.sites.Site

@Database(
    entities = [Article::class, Site::class, Interests::class, BookmarkArticles::class, Breaking::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao

    abstract fun sitesDao(): SitesDao

    abstract fun interestsDao(): InterestsDao

    abstract fun articlesBookmarkDao(): BookmarkArticlesDao

    abstract fun breakingNewsDao(): BreakingNewsDao

}