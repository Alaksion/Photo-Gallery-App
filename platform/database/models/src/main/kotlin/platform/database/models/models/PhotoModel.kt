package platform.database.models.models

import android.net.Uri


sealed class PhotoModel {
    data class Remote(val url: String) : PhotoModel()
    data class Local(val uri: Uri) : PhotoModel()
}
