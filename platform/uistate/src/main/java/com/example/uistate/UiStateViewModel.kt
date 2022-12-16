package com.example.uistate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

interface UiStateOwner<T> {
    val uiState: StateFlow<UiState<T>>

    val stateData: T
}

abstract class UiStateViewModel<T>(
    initialState: T,
    private val dispatcher: CoroutineDispatcher
) : ViewModel(), UiStateOwner<T> {

    private val mutableUiState = MutableStateFlow(
        UiState(
            data = initialState,
            uiState = UiStateType.Content
        )
    )
    override val uiState: StateFlow<UiState<T>> = mutableUiState.asStateFlow()

    override val stateData: T
        get() = mutableUiState.value.data

    private val stateUpdater by lazy { StateManagerImpl(mutableUiState) }

    /**
     * Use this function to update the UiState once. Any unhandled exception thrown will automatically update the
     * UiState to [UiState.Error].
     *
     * @param showLoading Whether or not the loading state should be displayed while the suspend block is suspended. By default it's true.
     * @param block Suspend block responsible for the UiState update.
     * */
    protected fun setState(
        showLoading: Boolean = true,
        block: suspend (T) -> T,
    ) {
        updateState(
            showLoading = showLoading,
            block = { updater ->
                val resolvedBlock = block(mutableUiState.value.data)

                updater.updateState { currentState ->
                    currentState.copy(
                        data = resolvedBlock,
                        uiState = UiStateType.Content
                    )
                }
            }
        )
    }

    /**
     * Use this to update the state multiple times in a single block. Any unhandled exceptions will automatically
     * update the UiState to [UiState.Error].
     *
     * @param showLoading Whether or not the loading state should be set at the start of the suspended block. By default it's true
     * @param block Suspend block which provides the usage of [StateManagerImpl], helper class that allows multiple state updates.
     * */
    protected fun updateState(
        showLoading: Boolean = true,
        block: suspend (StateManagerImpl<T>) -> Unit
    ) {
        runSuspend {
            runCatching {
                if (showLoading) stateUpdater.updateStateType(UiStateType.Loading)
                block(stateUpdater)
            }.fold(
                onFailure = {
                    stateUpdater.updateStateType(UiStateType.Error(it))
                },
                onSuccess = {
                    stateUpdater.updateStateType(UiStateType.Content)
                }
            )
        }
    }

    /**
     * Use this function to run suspend code that won't update the UiState. Any unhandled exceptions will set the
     * UiState to [UiState.Error] automatically.
     *
     * @param showLoading Whether or not the loading state should be set at the start of the suspended block. By default it's true
     * @param block Suspend block to be executed
     * */
    protected fun runSuspendCatching(
        showLoading: Boolean = true,
        block: suspend () -> Unit
    ) {
        runSuspend {
            runCatching {
                if (showLoading) stateUpdater.updateStateType(UiStateType.Loading)
                block()
                stateUpdater.updateStateType(UiStateType.Content)
            }.onFailure {
                stateUpdater.updateStateType(UiStateType.Error(it))
            }
        }
    }

    private fun runSuspend(
        block: suspend () -> Unit
    ) {
        viewModelScope.launch(dispatcher) {
            block()
        }
    }

    /**
     * Convenience function to facilitate the collection of UiState using property delegation.
     * */
    @Composable
    fun collectUiState(): State<UiState<T>> {
        return this.mutableUiState.collectAsState()
    }

}

/**
 * Utility function to collect T from UiState ignoring Error and Loading emissions.
 * */
fun <T> StateFlow<UiState<T>>.filterForData(): Flow<T> = this.map { it.data }
