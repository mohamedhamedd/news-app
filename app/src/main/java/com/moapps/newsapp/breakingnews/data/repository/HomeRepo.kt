package com.moapps.newsapp.breakingnews.data.repository

import com.moapps.newsapp.breakingnews.data.remote.ApiEndPoints
import com.moapps.newsapp.breakingnews.data.models.news.Article
import com.moapps.newsapp.breakingnews.data.models.interests.Interests
import com.moapps.newsapp.breakingnews.data.models.news.News
import com.moapps.newsapp.breakingnews.data.local.ArticlesDao
import com.moapps.newsapp.breakingnews.data.local.InterestsDao
import retrofit2.Response

class HomeRepo(
        private val apiEndPoints: ApiEndPoints,
        private val articlesDao: ArticlesDao,
        private val interestsDao: InterestsDao
) {

    suspend fun makeInterestsApiCall(
            interest1: String,
            interest2: String,
            interest3: String,
            interest4: String
    ): Response<News> = apiEndPoints.getInterestsNews(
            interest1,
            interest2,
            interest3,
            interest4
    )

    //Get Interests
    fun getInterests() = interestsDao.getInterests()

    //Insert Interests for first time
    suspend fun insertDefaultInterests(interests: Interests) = interestsDao.insertInterests(interests)

    //Home Article Cache
    fun getHomeArticles(): List<Article> = articlesDao.getAllArticles()

    suspend fun insertHomeArticles(article: Article) {
        articlesDao.insertArticles(article)
    }

    suspend fun deleteHomeArticles() {
        articlesDao.deleteArticles()
    }

}