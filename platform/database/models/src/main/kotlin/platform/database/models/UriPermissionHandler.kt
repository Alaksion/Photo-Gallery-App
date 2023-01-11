package platform.database.models

import android.content.Context
import android.content.Intent
import android.net.Uri

internal class UriPermissionHandler(
    private val context: Context
) {

    fun registerPersistentPermission(uri: Uri) {
        context.grantUriPermission(context.packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.contentResolver.takePersistableUriPermission(
            uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
    }

}
