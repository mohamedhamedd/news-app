package com.moapps.newsapp.breakingnews.data.repository

import com.moapps.newsapp.breakingnews.data.local.BookmarkArticlesDao
import com.moapps.newsapp.breakingnews.data.models.news.Article
import com.moapps.newsapp.breakingnews.data.remote.ApiEndPoints

class WebviewRepo(
    private val bookmarkArticlesDao: BookmarkArticlesDao,
    private val apiEndPoints: ApiEndPoints
) {

    suspend fun insertArticle(article: Article) =
        bookmarkArticlesDao.insertArticles(article)

    suspend fun convertGoogleNewsLink(url: String) = apiEndPoints.convertGoogleNewsLink(url)

}