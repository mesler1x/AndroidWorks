package ru.mesler.androidworks.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.mesler.androidworks.api.response.MovieFullResponse
import ru.mesler.androidworks.domain.model.Premiere
import ru.mesler.androidworks.domain.model.Rating

@Entity
class MovieDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "movieName") val name: String?,
    @ColumnInfo(name = "movieType") val type: String?,
    @ColumnInfo(name = "movieGenres") val genre: String?,
    @ColumnInfo(name = "movieUrl") val url: String?,
    @ColumnInfo(name = "moviePremiere") val premiere: String?,
    @ColumnInfo(name = "movieRating") val rating: Double?
)