package com.tooltip.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntSize
import com.tooltip.utils.DEFAULT_CORNER_RADIUS
import com.tooltip.utils.DEFAULT_SCREEN_PADDING

/**
 * @param modifier Setup modifier.
 * @param mainContent Main screen content.
 * @param paddingHighlightArea Padding for highlighted area.
 * @param cornerRadiusHighlightArea Focus corner radius highlighted area.
 * @param anyHintVisible Hint Visibility.
 * @param visibleHintCoordinates Coordinate of visible tip.
 */
@Composable
fun ToolTipScreen(
    modifier: Modifier = Modifier,
    mainContent: @Composable () -> Unit,
    paddingHighlightArea: Float = DEFAULT_SCREEN_PADDING,
    cornerRadiusHighlightArea: Float = DEFAULT_CORNER_RADIUS,
    anyHintVisible: Boolean,
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
            padding = paddingHighlightArea,
            cornerRadius = cornerRadiusHighlightArea
        )
    }
}

