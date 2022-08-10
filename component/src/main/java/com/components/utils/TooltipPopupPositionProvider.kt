package com.components.utils

import androidx.compose.ui.unit.*
import androidx.compose.ui.window.PopupPositionProvider
import com.components.core.AnchorEdge
import com.components.core.EdgePosition
import com.components.core.TooltipStyle

class TooltipPopupPositionProvider(
    private val density: Density,
    private val anchorEdge: AnchorEdge,
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
    ): IntOffset = anchorEdge.calculatePopupPosition(
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