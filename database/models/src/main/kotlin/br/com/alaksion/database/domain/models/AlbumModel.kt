package br.com.alaksion.database.domain.models

import java.time.LocalDate

data class AlbumModel(
    val id: Int,
    val name: String,
    val description: String,
    val createdAt: LocalDate,
    val updatedAt: LocalDate,
) {
    companion object {
        internal val fixture = AlbumModel(
            id = 0,
            name = "some name",
            description = "some descritption",
            createdAt = LocalDate.now(),
            updatedAt = LocalDate.now()
        )
    }
}
