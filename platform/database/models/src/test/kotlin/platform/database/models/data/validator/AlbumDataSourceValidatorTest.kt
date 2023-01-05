package platform.database.models.data.validator

import platform.error.InternalException
import platform.database.models.models.CreateAlbumModel
import org.junit.Test

internal class AlbumDataSourceValidatorTest {

    @Test(expected = InternalException.Generic::class)
    fun `Should throw exception when creating album with empty name`() {
        platform.database.models.data.validator.AlbumDataSourceValidator.validateCreateAlbumPayload(
            platform.database.models.models.CreateAlbumModel("", "description")
        )
    }

    @Test(expected = InternalException.Generic::class)
    fun `Should throw exception when creating album with empty description`() {
        platform.database.models.data.validator.AlbumDataSourceValidator.validateCreateAlbumPayload(
            platform.database.models.models.CreateAlbumModel("name", "")
        )
    }

    @Test
    fun `Should not throw exception when creating album with name and description`() {
        fun `Should throw exception when creating album with empty name`() {
            platform.database.models.data.validator.AlbumDataSourceValidator.validateCreateAlbumPayload(
                platform.database.models.models.CreateAlbumModel("name", "description")
            )
        }
    }

}
