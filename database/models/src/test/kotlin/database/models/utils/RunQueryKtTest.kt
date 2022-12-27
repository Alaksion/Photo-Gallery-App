package database.models.utils

import platform.error.InternalException
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class RunQueryKtTest {

    @Test(expected = InternalException::class)
    fun `Should throw internal exception when Generic type is captured`() = runTest {
        runQuery {
            throw InternalException.Generic("aaa")
        }
    }

    @Test
    fun `Should propagate unhandled exception when Throwable is captured`() = runTest {
        try {
            runQuery {
                throw IllegalArgumentException()
            }
        } catch (e: Throwable) {
            Truth.assertThat(e).isInstanceOf(InternalException.Untreated::class.java)
            Truth.assertThat((e as InternalException.Untreated).originalError)
                .isInstanceOf(IllegalArgumentException::class.java)
        }
    }

}
