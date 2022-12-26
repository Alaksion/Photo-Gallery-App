package database.models

import androidx.room.Database
import androidx.room.RoomDatabase
import database.models.data.entities.AlbumEntity
import database.models.data.entities.AlbumEntityDao

@Database(entities = [AlbumEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumEntityDao
}
