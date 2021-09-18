package com.moapps.newsapp.breakingnews.data.models.breaking

import com.google.gson.annotations.SerializedName

data class BreakingNews(
    @SerializedName("data")
    val breaking: List<Breaking>,
    val total_results: Int
)