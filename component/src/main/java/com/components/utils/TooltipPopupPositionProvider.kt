package com.components.utils

import androidx.compose.ui.unit.*
import androidx.compose.ui.window.PopupPositionProvider
import com.components.core.AnchorEdgeView
import com.components.core.EdgePosition
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
    private val anchorEdge: AnchorEdgeView,
    private val tooltipStyle: TooltipStyle,
    private val tipPosition: EdgePosition,
    private val anchorPosition: EdgePosition,
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