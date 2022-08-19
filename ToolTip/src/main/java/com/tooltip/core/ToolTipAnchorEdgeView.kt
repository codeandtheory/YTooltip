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

abstract class ToolTipAnchorEdgeView {

    /**
     * @param modifier : Custom modifier.
     * @param cornerRadius : Setup corner radius.
     * @param tipPosition : Set toolTip Position.
     * @param tip : Tip Composable View.
     * @param content : Content Composable View.
     * */
    @Composable
    internal abstract fun anchorEdgeViewTooltipContainer(
        modifier: Modifier,
        cornerRadius: Dp,
        tipPosition: ToolTipEdgePosition,
        tip: @Composable () -> Unit,
        content: @Composable () -> Unit
    )

    /**
     * @param density : Custom local density..
     * @param tooltipStyle : Style for ToolTipStyle.
     * @param tipPosition : ToolTip Position.
     * @param anchorPosition : AnchorPosition.
     * @param margin : Custom margin.
     * @param anchorBounds : IntRect - Left, Right, Top, Bottom.
     * @param layoutDirection : LayoutDirection ltr or rtl.
     * @param popupContentSize : PopupContent size.
     * @param statusBarHeight : StatusBarHeight.
     * */
    internal open fun popupPositionCalculation(
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

    /**
     * tip min width for Vertical Edge and max width for Horizontal Edge
     * */
    internal abstract fun selectWidth(width: Dp, height: Dp): Dp

    /**
     * tip max height for Vertical Edge and man height for Horizontal Edge
     * */
    internal abstract fun selectHeight(width: Dp, height: Dp): Dp

    /**
     * miniSize of Vertical or Horizontal Anchor Edge
     * */
    internal abstract fun Modifier.minSize(tooltipStyle: TooltipStyle): Modifier

    /**
     * draw tip on Path with size (height, width) and direction(ltr, rtl)
     * */
    internal abstract fun Path.drawTip(size: Size, layoutDirection: LayoutDirection)
}

/**
 * Get absoluteValue from current offset
 * */
internal val Dp.absoluteValue get() = if (this < 0.dp) -this else this