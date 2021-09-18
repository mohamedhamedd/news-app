package com.moapps.newsapp.breakingnews.data.models.news

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "articles_table")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val author: String,
    val date: String,
    val img: String,
    val source: String,
    val title: String,
    val url: String,
    val interest:String
): Serializable