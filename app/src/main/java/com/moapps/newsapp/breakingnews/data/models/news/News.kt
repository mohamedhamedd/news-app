package com.moapps.newsapp.breakingnews.data.models.news

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("data")
    val articles: List<Article>,
    val query: String,
    val total_results: Int
)