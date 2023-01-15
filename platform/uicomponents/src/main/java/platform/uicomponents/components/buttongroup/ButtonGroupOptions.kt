package platform.uicomponents.components.buttongroup

enum class ButtonGroupAxis {
    Vertical,
    Horizontal;
}

enum class ButtonGroupItemType {
    Regular,
    Outlined,
    Text;
}

/**
 * Configuration class for the [MviButtonGroup] buttons.
 * @param text Text to displayed on the button
 * @param type Type of button to be used. Can be of types [ButtonGroupItemType.Outlined],
 * [ButtonGroupItemType.Regular] or [ButtonGroupItemType.Text]. These options will be translated
 * to the Material 3 specification buttons.
 * @param onClick Function executed by the button when it's clicked.
 * @param enabled Whether or not the button is enabled. This parameter is optional and is true by default
 * */
data class ButtonGroupItem(
    val text: String,
    val type: ButtonGroupItemType,
    val onClick: () -> Unit,
    val enabled: Boolean = true
)
