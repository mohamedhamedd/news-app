package com.moapps.newsapp.breakingnews.data.repository

import com.moapps.newsapp.breakingnews.data.remote.ApiEndPoints

class BreakingViewerRepo(
    private val apiEndPoints: ApiEndPoints
) {

    suspend fun convertNewsNowLink(url: String) = apiEndPoints.convertNewsNowLink(url)

}