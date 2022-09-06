package com.tooltip.core

import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min


/**
 * @class ToolTipHorizontalAnchorEdge is child class of @class ToolTipAnchorEdgeView.
 * ToolTipHorizontalAnchorEdge is to set HorizontalAnchorEdge
 * @selectWidth - It will gives the minimum from width or height of Tip
 * @selectHeight - It will gives the maximum from width or height of Tip
 * @minSize - It will give the minimumSize for ToolTipContainer
 * @calculatePopupPositionX - Calculate the X popupPosition
 */
abstract class ToolTipHorizontalAnchorEdge : ToolTipAnchorEdgeView() {

    override fun selectWidth(width: Dp, height: Dp): Dp {
        return max(width, height)
    }

    override fun selectHeight(width: Dp, height: Dp): Dp {
        return min(width, height)
    }

    override fun Modifier.minSize(tooltipStyle: TooltipStyle): Modifier = with(tooltipStyle) {
        return widthIn(min = cornerRadius * 2 + max(tipWidth, tipHeight))
    }

    /**
     * Calculate the X position for Horizontal Edge
     * */
    protected fun calculatePopupPositionX(
        density: Density,
        layoutDirection: LayoutDirection,
        anchorBounds: IntRect,
        anchorPosition: ToolTipEdgePosition,
        tooltipStyle: TooltipStyle,
        tipPosition: ToolTipEdgePosition,
        popupContentSize: IntSize
    ): Float = with(density) {
        val contactPointX = if (layoutDirection == LayoutDirection.Ltr) {
            anchorBounds.left +
                    anchorBounds.width * anchorPosition.toolTipPercent +
                    anchorPosition.toolTipOffset.toPx()
        } else {
            anchorBounds.right -
                    anchorBounds.width * anchorPosition.toolTipPercent -
                    anchorPosition.toolTipOffset.toPx()
        }
        val tangentWidth = (tooltipStyle.cornerRadius * 2 +
                tipPosition.toolTipOffset.absoluteValue * 2 +
                max(tooltipStyle.tipWidth, tooltipStyle.tipHeight)).toPx()
        val tangentLeft = contactPointX - tangentWidth / 2
        val tipMarginLeft = (popupContentSize.width - tangentWidth) *
                if (layoutDirection == LayoutDirection.Ltr) {
                    tipPosition.toolTipPercent
                } else {
                    1f - tipPosition.toolTipPercent
                }
        return tangentLeft - tipMarginLeft
    }
}
