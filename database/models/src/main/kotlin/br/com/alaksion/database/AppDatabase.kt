package br.com.alaksion.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.alaksion.database.data.entities.AlbumEntity
import br.com.alaksion.database.data.entities.AlbumEntityDao

@Database(entities = [AlbumEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumEntityDao
}
