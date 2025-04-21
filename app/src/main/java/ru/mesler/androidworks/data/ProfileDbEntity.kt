package ru.mesler.androidworks.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ProfileDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "profileFio") val fio: String?,
    @ColumnInfo(name = "profileAvatar") val avatarUri: String?,
    @ColumnInfo(name = "profileResume") val resumeUrl: String?,
    @ColumnInfo(name = "profilePosition") val position: String?,
    @ColumnInfo(name = "profileEmail") val email: String?
)