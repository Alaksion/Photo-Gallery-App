package features.photos.photopicker.presentation

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

internal object SuccessScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        UI { navigator?.popUntilRoot() }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    internal fun UI(
        returnToHome: () -> Unit
    ) {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Add photos") }) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .padding(MviSampleSizes.medium)
            ) {
                WeightSpacer(weight = 1f)

                Text(
                    text = "Photos added successfully",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "You can now see them in your album's page.",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                WeightSpacer(weight = 1f)
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = returnToHome
                ) {
                    Text("Return to Home")
                }
            }
        }
    }

}
