package platform.database.models.data.datasources

import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import platform.database.models.models.album.AlbumModel
import platform.database.models.models.album.CreateAlbumModel
import platform.error.InternalException

@ExperimentalCoroutinesApi
internal class AlbumDataSourceImplementationTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var dataSource: platform.database.models.data.datasources.AlbumDataSource
    private lateinit var albumDao: AlbumEntityDaoMock
    private val validator = platform.database.models.data.validator.AlbumDataSourceValidator

    @Before
    fun setUp() {
        albumDao = AlbumEntityDaoMock()

        dataSource = platform.database.models.data.datasources.AlbumDataSourceImplementation(
            albumDao,
            validator
        )
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Should call DAO when get all is called`() = runTest(testDispatcher) {
        dataSource.getAll()

        Truth.assertThat(albumDao.getAllCalls).isEqualTo(1)
    }

    @Test
    fun `Should call DAO when get by id is called`() = runTest(testDispatcher) {
        dataSource.getById(10)

        Truth.assertThat(albumDao.getByIdCalls).isEqualTo(1)
    }

    @Test
    fun `Should call DAO when create is called`() = runTest(testDispatcher) {
        dataSource.create(CreateAlbumModel("name", "description"))

        Truth.assertThat(albumDao.createCalls).isEqualTo(1)
    }

    @Test(expected = InternalException.Generic::class)
    fun `Should throw exception when creating album with empty name`() = runTest(testDispatcher) {
        dataSource.create(CreateAlbumModel("", "aa"))
    }

    @Test(expected = InternalException.Generic::class)
    fun `Should throw exception when creating album with empty description`() =
        runTest(testDispatcher) {
            dataSource.create(CreateAlbumModel("aa", ""))
        }

    @Test
    fun `Should call DAO when delete is called`() = runTest(testDispatcher) {
        dataSource.delete(AlbumModel.fixture)

        Truth.assertThat(albumDao.deleteCalls).isEqualTo(1)
    }

}
