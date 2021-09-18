package com.moapps.newsapp.breakingnews.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.moapps.newsapp.breakingnews.data.remote.ApiEndPoints
import com.moapps.newsapp.breakingnews.data.remote.NetworkConnectionInterceptor
import com.moapps.newsapp.breakingnews.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesNetworkInterceptor(@ApplicationContext context: Context): NetworkConnectionInterceptor = NetworkConnectionInterceptor(context)

    @Singleton
    @Provides
    fun providesOkHttpClient(networkConnectionInterceptor: NetworkConnectionInterceptor): OkHttpClient =
            OkHttpClient
                    .Builder()
                    .addInterceptor(networkConnectionInterceptor)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .build()

    @Singleton
    @Provides
    fun provideNewsApiEnds(okHttpClient: OkHttpClient): ApiEndPoints = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient)
            .build()
            .create(ApiEndPoints::class.java)

}