package features.photos.photopicker.utils

import android.content.Context
import android.content.Intent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

internal class MultiMediaContract(
    maxItems: Int
) : ActivityResultContracts.PickMultipleVisualMedia(maxItems) {

    override fun createIntent(context: Context, input: PickVisualMediaRequest): Intent {
        val intent = super.createIntent(context, input)
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        return intent
    }

}
