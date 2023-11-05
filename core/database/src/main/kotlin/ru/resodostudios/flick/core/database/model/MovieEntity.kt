package ru.resodostudios.flick.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.runInterruptible
import ru.resodostudios.flick.core.model.data.FavoriteMovie
import ru.resodostudios.flick.core.model.data.Image
import ru.resodostudios.flick.core.model.data.Movie
import ru.resodostudios.flick.core.model.data.Network
import ru.resodostudios.flick.core.model.data.Rating

@Entity(
    tableName = "favorite_movies"
)
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val averageRuntime: Int,
    val ended: String,
    val genres: List<String>,
    val image: Image,
    val language: String,
    val name: String,
    val network: Network,
    val officialSite: String,
    val premiered: String,
    val rating: Rating,
    val runtime: Int,
    val status: String,
    val summary: String
)

fun MovieEntity.asExternalModel() = Movie(
    id = id,
    averageRuntime = averageRuntime,
    ended = ended,
    genres = genres,
    image = image,
    language = language,
    name = name,
    network = network,
    officialSite = officialSite,
    premiered = premiered,
    rating = rating,
    runtime = runtime,
    status = status,
    summary = summary
)