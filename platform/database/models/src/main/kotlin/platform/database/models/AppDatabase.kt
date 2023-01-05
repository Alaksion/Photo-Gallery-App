package platform.database.models

import androidx.room.Database
import androidx.room.RoomDatabase
import platform.database.models.data.entities.AlbumEntity
import platform.database.models.data.entities.AlbumEntityDao
import platform.database.models.data.entities.PhotoEntityDao
import platform.database.models.data.entities.PhotoEntity

@Database(entities = [AlbumEntity::class, PhotoEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumEntityDao

    abstract fun photoDao(): PhotoEntityDao
}
