package platform.uistate.uistate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

interface UiStateOwner<T> {

    /**
     * StateFlow containing the current snapshot of [UiState] managed by this class.
     */
    val uiState: StateFlow<UiState<T>>

    /**
     * Read only property to read [T] from this UiStateOwner directly.
     * */
    val stateData: T

    /**
     * Provides a [StateManager] scope to allow synchronous state updates. Unhandled exceptions will
     * automatically update the UiState to [UiState.Error].
     *
     * @param block Suspend block which provides the usage of [StateManagerImpl], helper class that
     * allows multiple state updates.
     * */
    fun updateState(block: StateManagerImpl<T>.() -> Unit)

    /**
     * Provides a [StateManager] scope to allow asynchronous state updates. Unhandled exceptions will
     * automatically update the UiState to [UiStateType.Error].
     *
     * @param showLoading Whether or not the [UiStateType] should be updated to [UiStateType.Loading]
     * during the block execution. By default this value is set to true.
     *
     * @param block Suspend block which provides the usage of [StateManagerImpl], helper class that
     * allows multiple state updates.
     * */
    suspend fun asyncUpdateState(
        showLoading: Boolean = true,
        block: suspend StateManagerImpl<T>.() -> Unit
    )

    /**
     * Run suspend code that won't update the state data, like adding [UiEvent]. Unhandled exceptions
     * will update the [UiStateType] to [UiStateType.Error] automatically.
     * */
    suspend fun asyncRunCatching(
        showLoading: Boolean = true,
        block: suspend () -> Unit
    )
}

class UiStateHandler<T>(initialState: T) : UiStateOwner<T> {

    private val mutableUiState = MutableStateFlow(
        UiState(
            data = initialState,
            uiState = UiStateType.Content
        )
    )
    override val uiState: StateFlow<UiState<T>> = mutableUiState.asStateFlow()

    override val stateData: T
        get() = mutableUiState.value.data

    override suspend fun asyncRunCatching(
        showLoading: Boolean,
        block: suspend () -> Unit
    ) {
        kotlin.runCatching {
            if (showLoading) stateUpdater.updateStateType(UiStateType.Loading)
            block()
            stateUpdater.updateStateType(UiStateType.Content)
        }.onFailure {
            stateUpdater.updateStateType(UiStateType.Error(it))
        }
    }

    private val stateUpdater by lazy { StateManagerImpl(mutableUiState) }

    override suspend fun asyncUpdateState(
        showLoading: Boolean,
        block: suspend StateManagerImpl<T>.() -> Unit
    ) {
        kotlin.runCatching {
            if (showLoading) stateUpdater.updateStateType(UiStateType.Loading)
            block(stateUpdater)
            stateUpdater.updateStateType(UiStateType.Content)
        }.onFailure {
            stateUpdater.updateStateType(UiStateType.Error(it))
        }
    }

    override fun updateState(block: StateManagerImpl<T>.() -> Unit) {
        kotlin.runCatching {
            block(stateUpdater)
            stateUpdater.updateStateType(UiStateType.Content)
        }.onFailure {
            stateUpdater.updateStateType(UiStateType.Error(it))
        }
    }

}

/**
 * Convenience function to collect UiState using property delegation
 * */
@Composable
fun <T> UiStateOwner<T>.collectState(): State<UiState<T>> {
    return uiState.collectAsState()
}

/**
 * Utility function to collect T from UiState ignoring Error and Loading emissions.
 * */
fun <T> StateFlow<UiState<T>>.filterForData(): Flow<T> = this.map { it.data }
