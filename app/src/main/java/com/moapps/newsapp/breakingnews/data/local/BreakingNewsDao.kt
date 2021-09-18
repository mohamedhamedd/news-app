package com.moapps.newsapp.breakingnews.data.local

import androidx.room.*
import com.moapps.newsapp.breakingnews.data.models.breaking.Breaking
import com.moapps.newsapp.breakingnews.data.models.news.Article


//To cache the articles in local database
@Dao
interface BreakingNewsDao {

    @Query("SELECT * FROM breaking_news")
    fun getAllBreakingNews(): List<Breaking>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreakingNews(breaking: Breaking)

    @Query("DELETE FROM breaking_news")
    suspend fun deleteBreakingNews()

}