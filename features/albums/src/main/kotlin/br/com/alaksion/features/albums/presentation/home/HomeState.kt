package br.com.alaksion.features.albums.presentation.home

import database.models.models.AlbumModel

internal data class HomeState(
    val isInitialized: Boolean = false,
    val albums: List<AlbumModel> = listOf(),
)
