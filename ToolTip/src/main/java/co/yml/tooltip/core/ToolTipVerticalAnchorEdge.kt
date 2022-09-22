package co.yml.tooltip.core

import androidx.compose.foundation.layout.heightIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.max

/**
 * @class ToolTipVerticalAnchorEdge is child class of @class ToolTipAnchorEdgeView.
 * ToolTipVerticalAnchorEdge is to set VerticalAnchorEge
 * @selectWidth - It will gives the minimum from width or height of Tip
 * @selectHeight - It will gives the maximum from width or height of Tip
 * @minSize - It will give the minimumSize for ToolTipContainer
 * @calculatePopupPositionY - Calculate the Y popupPosition
 */
abstract class ToolTipVerticalAnchorEdge : ToolTipAnchorEdgeView() {

    override fun selectWidth(width: Dp, height: Dp): Dp {
        return min(width, height)
    }

    override fun selectHeight(width: Dp, height: Dp): Dp {
        return max(width, height)
    }

    override fun Modifier.minSize(tooltipStyle: TooltipStyle): Modifier = with(tooltipStyle) {
        return heightIn(min = cornerRadius * 2 + max(tipWidth, tipHeight))
    }

    /**
     * Calculate the Y position for Vertical Edge
     * */
    protected fun calculatePopupPositionY(
        density: Density,
        anchorBounds: IntRect,
        anchorPosition: ToolTipEdgePosition,
        tooltipStyle: TooltipStyle,
        tipPosition: ToolTipEdgePosition,
        popupContentSize: IntSize
    ): Float = with(density) {
        val contactPointY = anchorBounds.top +
                anchorBounds.height * anchorPosition.toolTipPercent +
                anchorPosition.toolTipOffset.toPx()
        val tangentHeight = (tooltipStyle.cornerRadius * 2 +
                tipPosition.toolTipOffset.absoluteValue * 2 +
                max(tooltipStyle.tipWidth, tooltipStyle.tipHeight)).toPx()
        val tangentY = contactPointY - tangentHeight / 2
        val tipMarginY = (popupContentSize.height - tangentHeight) * tipPosition.toolTipPercent
        return tangentY - tipMarginY
    }
}
