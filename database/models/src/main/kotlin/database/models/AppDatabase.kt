package database.models

import androidx.room.Database
import androidx.room.RoomDatabase
import database.models.data.entities.AlbumEntity
import database.models.data.entities.AlbumEntityDao
import database.models.data.entities.PhotoEntity

@Database(entities = [AlbumEntity::class, PhotoEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumEntityDao
}
