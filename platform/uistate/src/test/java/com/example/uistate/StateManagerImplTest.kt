package com.example.uistate

import com.google.common.truth.Truth
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Test
import platform.uistate.uistate.StateManagerImpl
import platform.uistate.uistate.UiState
import platform.uistate.uistate.UiStateType

internal class StateManagerImplTest {

    private lateinit var manager: StateManagerImpl<SampleState>
    private lateinit var stateFlow: MutableStateFlow<UiState<SampleState>>

    @Before
    fun setUp() {
        stateFlow = MutableStateFlow(UiState(data = SampleState(), uiState = UiStateType.Content))
        manager = StateManagerImpl(stateFlow)
    }

    @Test
    fun `Should only update data when updateData is called`() {
        manager.updateData { it.copy(text = "edited") }

        Truth.assertThat(stateFlow.value.data).isEqualTo(SampleState(text = "edited"))
        Truth.assertThat(stateFlow.value.uiState).isEqualTo(UiStateType.Content)
    }

    @Test
    fun `Should only update UiStateType when updateStateType is called`() {
        manager.updateStateType(UiStateType.Loading)

        Truth.assertThat(stateFlow.value.uiState).isEqualTo(UiStateType.Loading)
        Truth.assertThat(stateFlow.value.data).isEqualTo(SampleState())
    }

    @Test
    fun `Should update UiStateType and data when updateState is called`() {
        manager.updateState { state ->
            state.copy(
                data = state.data.copy(text = "edited"),
                uiState = UiStateType.Loading
            )
        }

        Truth.assertThat(stateFlow.value.uiState).isEqualTo(UiStateType.Loading)
        Truth.assertThat(stateFlow.value.data).isEqualTo(SampleState(text = "edited"))
    }

}
