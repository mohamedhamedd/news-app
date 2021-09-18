package com.moapps.newsapp.breakingnews.data.models.bookmark

import androidx.room.Embedded
import androidx.room.Entity
import com.moapps.newsapp.breakingnews.data.models.news.Article


@Entity(tableName = "articles_bookmark_table", primaryKeys = ["id"])
data class BookmarkArticles(
    @Embedded
    val article: Article
)