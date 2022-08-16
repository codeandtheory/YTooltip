package com.components.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntSize
import com.components.utils.SCREEN_ALPHA
import com.components.utils.TRANSPARENT_ALPHA
import com.components.utils.DEFAULT_CORNER_RADIUS
import com.components.utils.DEFAULT_SCREEN_PADDING

/**
 * @param modifier Setup modifier
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

/**
 * @param anyHintVisible Hint visibility
 * @param viewOffset Offset from root.
 * @param viewOffsetSize Offset size to tip coordinate .
 * @param cornerRadius Corner radius for focus area.
 * @param padding Padding for offset.
 */
@Composable
fun DrawCanvas(
    anyHintVisible: Boolean,
    viewOffset: Offset,
    viewOffsetSize: IntSize,
    cornerRadius: Float,
    padding: Float
) {
    val vOffsetSize = Size(
        viewOffsetSize.width.toFloat() + padding,
        viewOffsetSize.height.toFloat() + padding
    )
    val vOffset =
        Offset(viewOffset.x - padding / 2, viewOffset.y - padding / 2)


    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                alpha = SCREEN_ALPHA
            }
    ) {
        drawRect(
            color = if (anyHintVisible) Color.Black.copy(TRANSPARENT_ALPHA) else Color.Transparent,
        )
        drawRoundRect(
            size = vOffsetSize,
            color = Color.Transparent,
            topLeft = vOffset,
            cornerRadius = CornerRadius(cornerRadius, cornerRadius),
            blendMode = BlendMode.Clear
        )
    }
}