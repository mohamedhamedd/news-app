package com.moapps.newsapp.breakingnews.data.repository

import com.moapps.newsapp.breakingnews.data.local.InterestsDao
import com.moapps.newsapp.breakingnews.data.models.interests.Interests

class SettingsRepo(
    private val interestsDao: InterestsDao
) {

    fun getInterests() = interestsDao.getInterests()

    suspend fun insertInterests(interests: Interests) = interestsDao.insertInterests(interests)

}