package features.photos.presentation.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import platform.uicomponents.components.errorview.DefaultErrorView
import platform.uicomponents.components.errorview.DefaultErrorViewButton
import platform.uicomponents.components.errorview.DefaultErrorViewOptions
import platform.uistate.uistate.UiStateContent
import platform.uistate.uistate.collectState

internal data class PhotoDetailsScreen(
    private val photoId: Int
) : AndroidScreen() {

    @Composable
    override fun Content() {
        val model = getViewModel<PhotoDetailsViewModel>()
        val state by model.collectState()
        val navigator = LocalNavigator.current

        state.UiStateContent(
            stateContent = { StateContent(state = it) },
            errorState = {
                DefaultErrorView(
                    error = it,
                    options = DefaultErrorViewOptions(
                        primaryButton = DefaultErrorViewButton(
                            title = "Try Again",
                            onClick = {
                                model.handleIntent(PhotoDetailsIntent.LoadData(photoId))
                            }
                        ),
                        secondaryButton = DefaultErrorViewButton(
                            title = "Return to Home",
                            onClick = {
                                navigator?.popUntilRoot()
                            }
                        )
                    )
                )
            }
        )

    }

    @Composable
    fun StateContent(
        state: PhotoDetailsState
    ) {

    }

}
