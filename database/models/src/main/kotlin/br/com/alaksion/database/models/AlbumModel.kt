package br.com.alaksion.database.models

import java.time.LocalDate

data class AlbumModel(
    val id: Int,
    val name: String,
    val description: String,
    val createdAt: LocalDate,
    val updatedAt: LocalDate,
)
