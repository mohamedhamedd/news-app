package com.moapps.newsapp.breakingnews.data.local

import androidx.room.*
import com.moapps.newsapp.breakingnews.data.models.news.Article


//To cache the articles in local database
@Dao
interface ArticlesDao {

    @Query("SELECT * FROM articles_table")
    fun getAllArticles(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(article: Article)

    @Query("DELETE FROM articles_table")
    suspend fun deleteArticles()

}