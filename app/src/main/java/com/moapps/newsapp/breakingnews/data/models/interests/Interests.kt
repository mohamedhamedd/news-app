package com.moapps.newsapp.breakingnews.data.models.interests

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "interests_table")
data class Interests(
        @PrimaryKey(autoGenerate = false)
        val id: Int = 0,
        val interest1: String,
        val interest2: String,
        val interest3: String,
        val interest4: String,
)
