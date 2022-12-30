package features.albums.create.presentation

internal data class CreateAlbumState(
    val name: String = "",
    val description: String = "",
) {
    val isButtonEnabled = name.isNotEmpty() && description.isNotEmpty()
}
