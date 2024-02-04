package com.example.restflickrcompose.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.restflickrcompose.data.database.dao.PhotoDao
import com.example.restflickrcompose.data.database.model.PhotoEntity

@Database(entities = [PhotoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}