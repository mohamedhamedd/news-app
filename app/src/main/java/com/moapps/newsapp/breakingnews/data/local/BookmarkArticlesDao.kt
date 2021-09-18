package com.moapps.newsapp.breakingnews.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.moapps.newsapp.breakingnews.data.models.bookmark.BookmarkArticles
import com.moapps.newsapp.breakingnews.data.models.news.Article


//represents bookmarks
@Dao
interface BookmarkArticlesDao {

    @Query("SELECT * FROM articles_bookmark_table")
    fun getAllArticles(): LiveData<List<Article>>

    @Insert(entity = BookmarkArticles::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(article: Article)

    @Delete(entity = BookmarkArticles::class)
    suspend fun deleteArticles(article: Article)

}