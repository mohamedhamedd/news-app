package com.moapps.newsapp.breakingnews.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moapps.newsapp.breakingnews.data.models.sites.Site


//To cache a sites in local database
@Dao
interface SitesDao {

    @Query("SELECT * FROM sites_table")
    fun getAllSites(): List<Site>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSites(site: Site)

    @Query("DELETE FROM sites_table")
    suspend fun deleteArticles()

}