package ru.mesler.androidworks.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProfileDao {
    @Query("SELECT * FROM ProfileDbEntity LIMIT 1")
    suspend fun getUser(): ProfileDbEntity?

    @Insert
    suspend fun insert(driverDbEntity: ProfileDbEntity)

    @Query("UPDATE ProfileDbEntity SET profileFio = :fio, profileAvatar = :avatarUri, profileResume = :resumeUrl, profilePosition = :position, profileEmail = :email")
    suspend fun update(fio: String?, avatarUri: String?, resumeUrl: String?, position: String?, email: String?)
}