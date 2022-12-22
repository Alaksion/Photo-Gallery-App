package br.com.alaksion.database.converters

import androidx.room.TypeConverter
import java.util.UUID

internal class UUIDConverter {

    fun uuidToString(id: UUID): String {
        return id.toString()
    }

    @TypeConverter
    fun stringToUiid(string: String): UUID {
        return UUID.fromString(string)
    }

}