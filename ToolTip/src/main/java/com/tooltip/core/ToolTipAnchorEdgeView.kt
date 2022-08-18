package com.tooltip.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainScope
import androidx.constraintlayout.compose.ConstrainedLayoutReference

abstract class ToolTipAnchorEdgeView {
    @Composable
    internal abstract fun AnchorEdgeViewTooltipContainer(
        modifier: Modifier,
        cornerRadius: Dp,
        tipPosition: ToolTipEdgePosition,
        tip: @Composable () -> Unit,
        content: @Composable () -> Unit
    )

    internal open fun popupPositionCalculate(
        density: Density,
        tooltipStyle: TooltipStyle,
        tipPosition: ToolTipEdgePosition,
        anchorPosition: ToolTipEdgePosition,
        margin: Dp,
        anchorBounds: IntRect,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize,
        statusBarHeight: Int
    ): IntOffset = IntOffset(0, 0)

    internal abstract fun selectWidth(width: Dp, height: Dp): Dp
    internal abstract fun selectHeight(width: Dp, height: Dp): Dp
    internal abstract fun Modifier.minSize(tooltipStyle: TooltipStyle): Modifier
    internal abstract fun Path.drawTip(size: Size, layoutDirection: LayoutDirection)
}

internal val Dp.absoluteValue get() = if (this < 0.dp) -this else this