package com.example.mvisample

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import br.com.alaksion.database.datasources.AlbumDataSource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mvisample.ui.theme.MviSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val CellAmount = 3

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataSource: AlbumDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MviSampleTheme {
                val list = remember {
                    mutableStateOf(listOf<Uri>())
                }
                val context = LocalContext.current

                val picker =
                    rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.PickVisualMedia(),
                        onResult = { Uri ->
                            Uri?.let { nullSafeUri ->
                                list.value = list.value + nullSafeUri
                                Log.d("image", nullSafeUri.toString())
                                Log.d("images", list.value.toString())
                            }
                        }
                    )

                Scaffold() {
                    Column(Modifier.padding(it)) {
                        Button(
                            onClick = {
                                picker.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Open image pcker")
                        }
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(CellAmount),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(it),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            items(list.value) { imageUri ->
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(imageUri)
                                        .build(),
                                    contentDescription = null,
                                    modifier = Modifier.height(150.dp),
                                    contentScale = ContentScale.Crop,
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}
