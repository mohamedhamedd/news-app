package com.moapps.newsapp.breakingnews.data.repository

import com.moapps.newsapp.breakingnews.data.local.SitesDao
import com.moapps.newsapp.breakingnews.data.models.news.News
import com.moapps.newsapp.breakingnews.data.models.sites.NewsSites
import com.moapps.newsapp.breakingnews.data.models.sites.Site
import com.moapps.newsapp.breakingnews.data.remote.ApiEndPoints
import retrofit2.Response

class SearchRepo(
        private val apiEndPoints: ApiEndPoints,
        private val sitesDao: SitesDao,
) {

    suspend fun queryForNews(query:String):Response<News> {
        return apiEndPoints.searchNews(query)
    }

    suspend fun getSites(): Response<NewsSites> = apiEndPoints.getSites()

    fun getAllSitesCache(): List<Site> = sitesDao.getAllSites()

    suspend fun insertAllCacheSites(site: Site) = sitesDao.insertSites(site)

    suspend fun deleteAllCacheSites() = sitesDao.deleteArticles()

}