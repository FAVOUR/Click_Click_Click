package com.example.clickclickclick.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.clickclickclick.db.entity.Title


@Dao
interface TitleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertTitle(title: Title)

    @get:Query("Select * From Title where id=0")
    val titleLiveData: LiveData<Title?>




}