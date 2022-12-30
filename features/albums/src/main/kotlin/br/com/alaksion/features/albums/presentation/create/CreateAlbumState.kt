package br.com.alaksion.features.albums.presentation.create

internal data class CreateAlbumState(
    val name: String = "",
    val description: String = "",
) {
    val isButtonEnabled = name.isNotEmpty() && description.isNotEmpty()
}
