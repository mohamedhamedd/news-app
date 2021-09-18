package com.moapps.newsapp.breakingnews.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moapps.newsapp.breakingnews.data.models.interests.Interests


//Interests to save the interests of the user in local database
@Dao
interface InterestsDao {

    @Query("SELECT * FROM interests_table")
    fun getInterests(): LiveData<Interests>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInterests(interests: Interests)

}