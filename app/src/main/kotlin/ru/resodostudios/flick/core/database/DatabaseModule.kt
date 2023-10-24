package ru.resodostudios.flick.core.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFlickDatabase(
        @ApplicationContext context: Context,
    ): FlickDatabase = Room.databaseBuilder(
        context,
        FlickDatabase::class.java,
        "flick-database"
    )
        .addMigrations(DatabaseMigrations.MIGRATION_1_2)
        .build()
}