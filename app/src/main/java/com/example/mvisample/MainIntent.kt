package com.example.mvisample

internal sealed class MainIntent {
    object SendEvent: MainIntent()
    data class UpdateText(val text: String) : MainIntent()
}
