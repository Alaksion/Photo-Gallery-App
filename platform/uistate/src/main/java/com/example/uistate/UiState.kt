package com.example.uistate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KProperty

sealed class UiStateType {

    object Content : UiStateType()
    object Loading : UiStateType()
    data class Error(val error: Throwable) : UiStateType()

}


data class UiState<T>(
    val data: T,
    val uiState: UiStateType
)

@Composable
operator fun <T> StateFlow<UiState<T>>.getValue(thisObj: Any?, property: KProperty<*>): UiState<T> {
    return this.collectAsState().value
}
