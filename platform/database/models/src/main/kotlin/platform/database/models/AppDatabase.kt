package platform.database.models

import androidx.room.Database
import androidx.room.RoomDatabase
import platform.database.models.data.entities.AlbumEntity
import platform.database.models.data.entities.AlbumEntityDao
import platform.database.models.data.entities.PhotoEntity
import platform.database.models.data.entities.PhotoEntityDao

@Database(entities = [AlbumEntity::class, PhotoEntity::class], version = 1, exportSchema = false)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumEntityDao

    abstract fun photoDao(): PhotoEntityDao
}
