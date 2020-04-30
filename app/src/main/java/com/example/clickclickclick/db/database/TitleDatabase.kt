package com.example.clickclickclick.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.clickclickclick.db.dao.TitleDao
import com.example.clickclickclick.db.entity.Title

/**
 * Created by Olije Favour on 4/26/2020.
 */
@Database(entities = [Title::class],version = 1,exportSchema = false)
abstract class TitleDatabase:RoomDatabase(){

     abstract val titleDao:TitleDao
}



@Volatile
lateinit var INSTSNCE:TitleDatabase

  fun getDatabase(context: Context):TitleDatabase{

     synchronized(TitleDatabase::class) {

         if(!::INSTSNCE.isInitialized) {
             INSTSNCE = Room.databaseBuilder(context, TitleDatabase::class.java, "titles_db")
                 .fallbackToDestructiveMigration()
                 .build()
         }

     }

      return INSTSNCE
  }