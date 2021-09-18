package com.moapps.newsapp.breakingnews.di

import com.moapps.newsapp.breakingnews.data.local.*
import com.moapps.newsapp.breakingnews.data.remote.ApiEndPoints
import com.moapps.newsapp.breakingnews.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepoHome(
        apiEndPoints: ApiEndPoints,
        articlesDao: ArticlesDao,
        interestsDao: InterestsDao
    ): HomeRepo =
        HomeRepo(apiEndPoints, articlesDao, interestsDao)

    @Singleton
    @Provides
    fun provideRepoSearch(
        apiEndPoints: ApiEndPoints,
        sitesDao: SitesDao
    ): SearchRepo = SearchRepo(apiEndPoints, sitesDao)

    @Singleton
    @Provides
    fun provideSettingsRepo(interestsDao: InterestsDao) = SettingsRepo(interestsDao)

    @Singleton
    @Provides
    fun provideBookmarkRepo(bookmarkArticlesDao: BookmarkArticlesDao) =
        BookmarkRepo(bookmarkArticlesDao)

    @Singleton
    @Provides
    fun provideWebviewRepo(bookmarkArticlesDao: BookmarkArticlesDao, apiEndPoints: ApiEndPoints) =
        WebviewRepo(bookmarkArticlesDao, apiEndPoints)

    @Singleton
    @Provides
    fun provideRepoSites(
        apiEndPoints: ApiEndPoints
    ): SitesRepo = SitesRepo(apiEndPoints)

    @Singleton
    @Provides
    fun provideRepoBreaking(
        apiEndPoints: ApiEndPoints,
        breakingNewsDao: BreakingNewsDao
    ): BreakingRepo = BreakingRepo(apiEndPoints, breakingNewsDao)

    @Singleton
    @Provides
    fun provideRepoBreakingViewer(
        apiEndPoints: ApiEndPoints
    ): BreakingViewerRepo = BreakingViewerRepo(apiEndPoints)

}