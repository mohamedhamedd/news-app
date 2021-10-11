package com.moapps.newsapp.breakingnews.data.repository

import com.moapps.newsapp.breakingnews.data.local.BookmarkArticlesDao
import com.moapps.newsapp.breakingnews.data.models.news.Article
import com.moapps.newsapp.breakingnews.data.remote.ApiEndPoints

class SitesRepo(
    private val apiEndPoints: ApiEndPoints,
) {

    suspend fun getSiteResult(query_site: String) = apiEndPoints.searchNews(query_site)

}