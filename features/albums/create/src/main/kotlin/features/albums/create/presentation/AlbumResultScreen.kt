package features.albums.create.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.spacers.WeightSpacer

internal enum class AlbumResult(
    val title: String,
    val description: String
) {
    Success("Album created successfully", "You can now add your favorite photos to it"),
    Error("Something went wrong", "Please try again in few minutes");
}

internal data class AlbumResultScreen(val result: AlbumResult) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Create New Album") }) }
        ) {
            val nav = LocalNavigator.current
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .padding(MviSampleSizes.medium)
            ) {
                WeightSpacer(weight = 1f)

                Text(
                    text = result.title,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = result.description,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                WeightSpacer(weight = 1f)
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { nav?.popUntilRoot() }
                ) {
                    Text("Return to Home")
                }
                if (result == AlbumResult.Error) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { nav?.pop() }
                    ) {
                        Text("Try Again")
                    }
                }
            }
        }
    }

}
