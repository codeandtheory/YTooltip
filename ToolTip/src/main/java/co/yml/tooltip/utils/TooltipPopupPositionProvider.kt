package co.yml.tooltip.utils

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.PopupPositionProvider
import co.yml.tooltip.core.ToolTipAnchorEdgeView
import co.yml.tooltip.core.ToolTipEdgePosition
import co.yml.tooltip.core.TooltipStyle

/**
 * @class TooltipPopupPositionProvider is used to provide Tooltip Popup Position on screen.
 * @function calculatePosition is used to calculate Tooltip Popup Position on screen.
 *
 *
 * @param density Custom local density.
 * @param anchorEdge AnchorEdge view.
 * @param tooltipStyle Custom tooltip style.
 * @param tipPosition Tip position.
 * @param anchorPosition Anchor position.
 * @param margin Tip margin.
 * @param statusBarHeight Status bar height.
 */
class TooltipPopupPositionProvider(
    private val density: Density,
    private val anchorEdge: ToolTipAnchorEdgeView,
    private val tooltipStyle: TooltipStyle,
    private val tipPosition: ToolTipEdgePosition,
    private val anchorPosition: ToolTipEdgePosition,
    private val margin: Dp,
    private val statusBarHeight: Int
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset = anchorEdge.popupPositionCalculation(
        density,
        tooltipStyle,
        tipPosition,
        anchorPosition,
        margin,
        anchorBounds,
        layoutDirection,
        popupContentSize,
        statusBarHeight
    )
}