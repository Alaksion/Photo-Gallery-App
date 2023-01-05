package platform.database.models.data.validator

import platform.database.models.models.CreateAlbumModel
import platform.error.InternalException

internal object AlbumDataSourceValidator {

    fun validateCreateAlbumPayload(data: CreateAlbumModel) {
        if (data.name.isEmpty())
            throw InternalException.Generic("Field name is obligatory")

        if (data.description.isEmpty())
            throw InternalException.Generic("Filed description is obligatory")
    }

}
