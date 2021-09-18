package com.moapps.newsapp.breakingnews.data.repository

import com.moapps.newsapp.breakingnews.data.local.BookmarkArticlesDao
import com.moapps.newsapp.breakingnews.data.models.news.Article

class BookmarkRepo(
        private val bookmarkArticlesDao: BookmarkArticlesDao
) {

    fun getAllArticles() = bookmarkArticlesDao.getAllArticles()

    suspend fun deleteArticle(article: Article) = bookmarkArticlesDao.deleteArticles(article)

}