package ru.mesler.androidworks.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieDbEntity")
    suspend fun getAll(): List<MovieDbEntity>

    @Insert
    suspend fun insert(driverDbEntity: MovieDbEntity)
}