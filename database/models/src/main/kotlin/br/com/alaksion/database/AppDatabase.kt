package br.com.alaksion.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alaksion.database.converters.UUIDConverter
import br.com.alaksion.database.models.AlbumEntity
import br.com.alaksion.database.models.AlbumEntityDao

@Database(entities = [AlbumEntity::class], version = 1)
@TypeConverters(UUIDConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumEntityDao
}