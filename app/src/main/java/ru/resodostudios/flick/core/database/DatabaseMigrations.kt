package ru.resodostudios.flick.core.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigrations {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE `favorite_people` (`name` TEXT NOT NULL, `image` TEXT NOT NULL, `id` INTEGER NOT NULL, " +
                    "PRIMARY KEY(`id`))")
        }
    }
}