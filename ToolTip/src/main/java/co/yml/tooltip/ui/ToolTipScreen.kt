package co.yml.tooltip.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntSize
import co.yml.tooltip.utils.DEFAULT_CORNER_RADIUS
import co.yml.tooltip.utils.DEFAULT_SCREEN_PADDING
import co.yml.tooltip.utils.TRANSPARENT_ALPHA

/**
 * Component that allow you to highlight specific item and show overlay.
 * <p>
 * ToolTipScreen Composable function will return @Composable (BoxScope).
 * @Composable (BoxScope) contains mainContent and DrawCanvas.
 * You MUST provide the value to @param mainContent.
 * @function mainContent is used to show content of screen with ToolTipView.
 * DrawCanvas is used to highlight specific item and draw overlay.
 *
 *
 * @param modifier Setup modifier.
 * @param mainContent Main screen content.
 * @param paddingHighlightArea Padding for highlighted area.
 * @param cornerRadiusHighlightArea Focus corner radius highlighted area.
 * @param anyHintVisible Hint Visibility.
 * @param visibleHintCoordinates Coordinate of visible tip.
 * @param backgroundTransparency Transparency for screen background.
 */
@Composable
fun ToolTipScreen(
    modifier: Modifier = Modifier,
    mainContent: @Composable () -> Unit,
    paddingHighlightArea: Float = DEFAULT_SCREEN_PADDING,
    cornerRadiusHighlightArea: Float = DEFAULT_CORNER_RADIUS,
    anyHintVisible: Boolean,
    backgroundTransparency: Float = TRANSPARENT_ALPHA,
    visibleHintCoordinates: MutableState<LayoutCoordinates?>,
) {
    val viewOffset: Offset = visibleHintCoordinates.value?.positionInRoot() ?: Offset(0f, 0f)
    val viewOffsetSize: IntSize = visibleHintCoordinates.value?.size ?: IntSize(0, 0)

    Box(
        modifier = modifier
    ) {
        mainContent()
        DrawCanvas(
            anyHintVisible = anyHintVisible,
            viewOffset = viewOffset,
            viewOffsetSize = viewOffsetSize,
            backgroundTransparency = backgroundTransparency,
            padding = paddingHighlightArea,
            cornerRadius = cornerRadiusHighlightArea
        )
    }
}


/**
 * @function that allow you to highlight specific item and show overlay.
 * <p>
 * @function isTipVisible will return MutableState<Boolean>.
 * You MUST provide the value to @param xs.
 * @function isTipVisible is used to find visibility of ToolTip on screen.
 * if one of xs value is true then isTipVisible will return true otherwise false.
 *
 * @param xs Setup modifier.
 */
@Composable
fun isTipVisible(vararg xs: MutableState<Boolean>): MutableState<Boolean> {
    var result = remember { mutableStateOf(false) }
    xs.forEach { if (it.value) result = it }
    return result
}

