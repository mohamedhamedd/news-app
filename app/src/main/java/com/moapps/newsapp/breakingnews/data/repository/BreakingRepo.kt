package com.moapps.newsapp.breakingnews.data.repository

import com.moapps.newsapp.breakingnews.data.local.BreakingNewsDao
import com.moapps.newsapp.breakingnews.data.models.breaking.Breaking
import com.moapps.newsapp.breakingnews.data.remote.ApiEndPoints

class BreakingRepo(
    private val apiEndPoints: ApiEndPoints,
    private val breakingNewsDao: BreakingNewsDao
) {

    suspend fun getBreakingNews() = apiEndPoints.getBreakingNews()

    fun getLocalBreakingNews() = breakingNewsDao.getAllBreakingNews()

    suspend fun insertLocalBreakingNews(breaking: Breaking) =
        breakingNewsDao.insertBreakingNews(breaking)

    suspend fun deleteLocalBreakingNews() = breakingNewsDao.deleteBreakingNews()

}