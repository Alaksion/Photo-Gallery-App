package features.albums.create.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import features.albums.create.presentation.steps.NameScreen

internal object CreateAlbumFlow : Screen {

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    override fun Content() {
        val viewModel = getViewModel<CreateViewModel>()

        LifecycleEffect(
            onDisposed = {
                viewModel.handleIntent(CreateAlbumIntent.ClearData)
            }
        )

        Navigator(screen = NameScreen) {
            SlideTransition(navigator = it)
        }
    }
}
