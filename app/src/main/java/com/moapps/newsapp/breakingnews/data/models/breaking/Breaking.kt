package com.moapps.newsapp.breakingnews.data.models.breaking

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "breaking_news")
data class Breaking(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val img: String,
    val source: String,
    val title: String,
    val url: String
) : Serializable