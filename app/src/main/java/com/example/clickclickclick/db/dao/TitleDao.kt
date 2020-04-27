package com.example.clickclickclick.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.clickclickclick.db.entity.Title


@Dao
interface TitleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTitle(title: Title)

    @Query("Select * From Title where id=0")
    fun getTitle():List<Title>


}