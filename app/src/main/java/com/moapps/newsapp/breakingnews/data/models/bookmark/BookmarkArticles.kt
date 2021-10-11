package com.moapps.newsapp.breakingnews.data.models.bookmark

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "articles_bookmark_table")
data class BookmarkArticles(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val author: String,
    val date: String,
    val img: String,
    val source: String,
    val title: String,
    val url: String,
    val interest:String
)