package br.com.alaksion.database.entities

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import br.com.alaksion.database.models.AlbumModel
import java.util.UUID

@Entity(tableName = "album")
internal data class AlbumEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "created_at")
    val createdAt: String,

    @ColumnInfo(name = "updated_at")
    val updatedAt: String
) {
    internal fun mapToModel(): AlbumModel {
        return AlbumModel(
            id = this.id,
            name = this.name,
            description = this.description,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }

    companion object {
        fun createFromModel(model: AlbumModel): AlbumEntity {
            return AlbumEntity(
                id = model.id,
                createdAt = model.createdAt,
                updatedAt = model.updatedAt,
                name = model.name,
                description = model.description
            )
        }
    }
}

@Dao
internal interface AlbumEntityDao {

    @Query("SELECT * FROM album")
    suspend fun getAll(): List<AlbumEntity>

    @Query("SELECT * FROM album where id = :albumId")
    suspend fun getById(albumId: UUID): AlbumEntity

    @Insert
    suspend fun create(album: AlbumEntity)

    @Delete
    suspend fun delete(album: AlbumEntity)
}
