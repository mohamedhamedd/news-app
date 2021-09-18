package com.moapps.newsapp.breakingnews.data.remote

import com.moapps.newsapp.breakingnews.data.models.breaking.BreakingNews
import com.moapps.newsapp.breakingnews.data.models.convertlink.ConvertLink
import com.moapps.newsapp.breakingnews.data.models.news.News
import com.moapps.newsapp.breakingnews.data.models.sites.NewsSites
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndPoints {

    @GET("news/search/")
    suspend fun searchNews(
        @Query("q") query: String,
    ): Response<News>

    @GET("news/interests/")  // news/interests/?interest1=egypt&interest2=usa&interest3=politics&interest4=L
    suspend fun getInterestsNews(
        @Query("interest1") interest1: String,
        @Query("interest2") interest2: String,
        @Query("interest3") interest3: String,
        @Query("interest4") interest4: String
    ): Response<News>

    @GET("news/sites/")
    suspend fun getSites(): Response<NewsSites>

    @GET("news/breakingNews/")
    suspend fun getBreakingNews(): Response<BreakingNews>

    @GET("/googleNewsLink/")
    suspend fun convertGoogleNewsLink(
        @Query("url") url: String
    ): Response<ConvertLink>

    @GET("/newsNowLink/")
    suspend fun convertNewsNowLink(
        @Query("url") url: String
    ): Response<ConvertLink>

}