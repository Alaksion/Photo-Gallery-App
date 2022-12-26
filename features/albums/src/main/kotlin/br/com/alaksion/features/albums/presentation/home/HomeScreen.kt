package br.com.alaksion.features.albums.presentation.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel

internal object HomeScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val model = getViewModel<HomeViewModel>()
        Text("Hello World")
    }

}
