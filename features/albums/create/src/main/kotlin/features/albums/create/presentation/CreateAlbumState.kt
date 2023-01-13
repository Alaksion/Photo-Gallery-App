package features.albums.create.presentation

import com.google.android.gms.maps.model.LatLng

internal data class CreateAlbumState(
    val name: String = "",
    val description: String = "",
    val location: LatLng = LatLng(0.0, 0.0)
) {
    val isButtonEnabled = name.isNotEmpty() && description.isNotEmpty()
}
