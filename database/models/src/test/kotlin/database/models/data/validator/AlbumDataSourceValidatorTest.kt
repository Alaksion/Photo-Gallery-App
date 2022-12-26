package database.models.data.validator

import com.example.error.InternalException
import database.models.models.CreateAlbumModel
import org.junit.Test

internal class AlbumDataSourceValidatorTest {

    @Test(expected = InternalException.Generic::class)
    fun `Should throw exception when creating album with empty name`() {
        AlbumDataSourceValidator.validateCreateAlbumPayload(
            CreateAlbumModel("", "description")
        )
    }

    @Test(expected = InternalException.Generic::class)
    fun `Should throw exception when creating album with empty description`() {
        AlbumDataSourceValidator.validateCreateAlbumPayload(
            CreateAlbumModel("name", "")
        )
    }

    @Test
    fun `Should not throw exception when creating album with name and description`() {
        fun `Should throw exception when creating album with empty name`() {
            AlbumDataSourceValidator.validateCreateAlbumPayload(
                CreateAlbumModel("name", "description")
            )
        }
    }

}