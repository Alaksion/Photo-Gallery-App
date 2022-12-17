package com.example.uievent

import app.cash.turbine.test
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
import java.util.UUID

private sealed class SampleEvents(override val eventId: UUID) : UiEvent {
    object Test2 : SampleEvents(UUID.randomUUID())
}

@ExperimentalCoroutinesApi
internal class UiEventHandlerImplTest {

    private lateinit var handler: UiEventHandler<SampleEvents>

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        handler = UiEventHandlerImpl()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Should add events to queue`() = runTest(dispatcher) {

        handler.events.test {
            skipItems(1)

            handler.enqueueEvent(SampleEvents.Test2)

            val state = awaitItem()

            Truth.assertThat(state).isEqualTo(listOf(SampleEvents.Test2))

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Should remove events from queue`() = runTest(dispatcher) {
        val event = SampleEvents.Test2

        handler.events.test {
            skipItems(1)

            handler.enqueueEvent(event)
            skipItems(1)

            handler.consumeEvent(event)
            val state = awaitItem()

            Truth.assertThat(state).isEqualTo(emptyList<SampleEvents>())

            cancelAndIgnoreRemainingEvents()
        }
    }
}
