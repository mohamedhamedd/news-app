package com.moapps.newsapp.breakingnews.data.models.sites

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "sites_table")
data class Site(
        @PrimaryKey(autoGenerate = true)
        val id:Int,
    val img: String,
    val name: String
):Serializable