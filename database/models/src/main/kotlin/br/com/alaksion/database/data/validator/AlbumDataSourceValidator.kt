package br.com.alaksion.database.data.validator

import br.com.alaksion.database.domain.models.CreateAlbumModel
import com.example.error.InternalException

internal object AlbumDataSourceValidator {

    fun validateCreateAlbumPayload(data: CreateAlbumModel) {
        if (data.name.isEmpty())
            throw InternalException.Generic("Field name is obligatory")

        if (data.description.isEmpty())
            throw InternalException.Generic("Filed description is obligatory")
    }

}