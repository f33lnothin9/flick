package ru.resodostudios.flick.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.resodostudios.flick.core.database.model.FavoritePersonEntity

@Dao
interface FavoritePeopleDao {

    @Upsert
    suspend fun upsertPerson(person: FavoritePersonEntity)

    @Delete
    suspend fun deletePerson(person: FavoritePersonEntity)

    @Query("SELECT * FROM favorite_people")
    fun getPeopleEntities(): Flow<List<FavoritePersonEntity>>
}