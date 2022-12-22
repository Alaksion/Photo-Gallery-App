package com.example.mvisample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import br.com.alaksion.database.data.datasources.AlbumDataSource
import br.com.alaksion.database.domain.models.AlbumModel
import com.example.mvisample.ui.theme.MviSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import javax.inject.Inject

const val CellAmount = 3

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataSource: AlbumDataSource

    private fun createSampleAlbum() {
        runBlocking {
            dataSource.create(
                AlbumModel(
                    id = 0,
                    createdAt = LocalDate.now(),
                    updatedAt = LocalDate.now(),
                    description = "aaaaaaa",
                    name = "sample title"
                )
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MviSampleTheme {


                Scaffold() {
                    Column(Modifier.padding(it)) {
                        Button(
                            onClick = { createSampleAlbum() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Save sample album")
                        }
                    }
                }
            }
        }
    }
}
