package com.components.utils

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.PopupPositionProvider
import com.components.core.ToolTipAnchorEdgeView
import com.components.core.ToolTipEdgePosition
import com.components.core.TooltipStyle

/**
 * @param density Custom density
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
    ): IntOffset = anchorEdge.popupPositionCalculate(
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