package platform.database.models.utils

import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import platform.error.InternalException
import platform.test.fixtures.TestLogger

@ExperimentalCoroutinesApi
internal class RunQueryKtTest {

    @Test(expected = InternalException::class)
    fun `Should throw internal exception when Generic type is captured`() = runTest {
        runQuery(TestLogger()) {
            throw InternalException.Generic("aaa")
        }
    }

    @Test
    fun `Should propagate unhandled exception when Throwable is captured`() = runTest {
        try {
            runQuery(TestLogger()) {
                throw IllegalArgumentException()
            }
        } catch (e: Throwable) {
            Truth.assertThat(e).isInstanceOf(InternalException.Untreated::class.java)
            Truth.assertThat((e as InternalException.Untreated).originalError)
                .isInstanceOf(IllegalArgumentException::class.java)
        }
    }

}
