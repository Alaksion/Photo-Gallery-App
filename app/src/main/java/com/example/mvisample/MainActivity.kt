package com.example.mvisample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mvisample.ui.theme.MviSampleTheme
import com.example.uievent.UiEventEffect
import com.example.uistate.UiStateContent
import com.example.uistate.getValue

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MviSampleTheme {
                val viewModel by viewModels<MainViewModel>(
                    factoryProducer = { MainViewModelProvider() }
                )
                val context = LocalContext.current
                val state by viewModel.uiState

                UiEventEffect(
                    eventHandler = viewModel,
                    onEventReceived = {
                        Toast.makeText(
                            context, it.name,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )

                state.UiStateContent(
                    stateContent = {
                        MainView(
                            state = it,
                            handleIntent = viewModel::handleIntent
                        )
                    },
                    errorState = {

                    }
                )
            }
        }
    }

    @Composable
    internal fun MainView(
        state: MainState,
        handleIntent: (MainIntent) -> Unit
    ) {
        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { handleIntent(MainIntent.SendEvent) }) {
                    Text("Send UI event")
                }
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = state.mock,
                    onValueChange = { text -> handleIntent(MainIntent.UpdateText(text)) },
                    modifier = Modifier.fillMaxWidth()

                )
            }
        }
    }
}