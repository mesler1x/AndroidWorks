package ru.mesler.androidworks.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProfileDbEntity::class], version = 1)
abstract class ProfileDatabase : RoomDatabase(){
    abstract fun profileDao(): ProfileDao
}