package com.example.mvisample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.mvisample.ui.theme.MviSampleTheme
import com.example.uievent.UiEventEffect

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MviSampleTheme {
                val viewModel by viewModels<MainViewModel>()
                val context = LocalContext.current

                UiEventEffect(
                    eventHandler = viewModel,
                    onEventReceived = {
                        Toast.makeText(
                            context, it.name,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )

                // A surface container using the 'background' color from the theme
                Scaffold {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Button(onClick = { /*TODO*/ }) {
                            Text("Send UI event")
                        }
                    }
                }
            }
        }
    }
}