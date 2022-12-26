package br.com.alaksion.features.albums.presentation.home

import br.com.alaksion.database.domain.models.AlbumModel

internal data class HomeState(
    val isInitialized: Boolean = false,
    val albums: List<AlbumModel> = listOf(),
)
