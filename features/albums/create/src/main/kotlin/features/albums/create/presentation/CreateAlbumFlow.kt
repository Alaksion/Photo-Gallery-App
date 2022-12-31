package features.albums.create.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.androidx.AndroidScreenLifecycleOwner
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleOwner
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleProvider
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import features.albums.create.presentation.steps.LocationScreen

internal object CreateAlbumFlow : Screen {

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    override fun Content() {
        Navigator(screen = LocationScreen) {
            SlideTransition(navigator = it)
        }
    }
}

/**
 * Provides a centralized ViewModelStoreOwner so other screens can also access previously created
 * viewModels.
 * */
internal class CreateAlbumFlowProvider : ScreenLifecycleProvider {
    override fun getLifecycleOwner(): ScreenLifecycleOwner {
        return AndroidScreenLifecycleOwner.get(CreateAlbumFlow)
    }

}
