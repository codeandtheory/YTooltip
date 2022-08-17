package com.components.core

import androidx.compose.foundation.layout.heightIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.max
import androidx.constraintlayout.compose.ConstrainScope
import androidx.constraintlayout.compose.ConstrainedLayoutReference

abstract class ToolTipVerticalAnchorEdge : ToolTipAnchorEdgeView() {
    override fun ConstrainScope.align(anchor: ConstrainedLayoutReference, bias: Float) {
        linkTo(anchor.top, anchor.bottom, bias = bias)
    }

    override fun ConstrainScope.nextTo(anchor: ConstrainedLayoutReference, margin: Dp) {
        top.linkTo(anchor.bottom, margin)
    }

    override fun ConstrainScope.beforeTo(anchor: ConstrainedLayoutReference, margin: Dp) {
        bottom.linkTo(anchor.top, margin)
    }

    override fun selectWidth(width: Dp, height: Dp): Dp {
        return min(width, height)
    }

    override fun selectHeight(width: Dp, height: Dp): Dp {
        return max(width, height)
    }

    override fun Modifier.minSize(tooltipStyle: TooltipStyle): Modifier = with(tooltipStyle) {
        return heightIn(min = cornerRadius * 2 + max(tipWidth, tipHeight))
    }

    protected fun calculatePopupPositionY(
        density: Density,
        anchorBounds: IntRect,
        anchorPosition: ToolTipEdgePosition,
        tooltipStyle: TooltipStyle,
        tipPosition: ToolTipEdgePosition,
        popupContentSize: IntSize
    ): Float = with(density) {
        val contactPointY = anchorBounds.top +
                anchorBounds.height * anchorPosition.percent +
                anchorPosition.offset.toPx()
        val tangentHeight = (tooltipStyle.cornerRadius * 2 +
                tipPosition.offset.absoluteValue * 2 +
                max(tooltipStyle.tipWidth, tooltipStyle.tipHeight)).toPx()
        val tangentY = contactPointY - tangentHeight / 2
        val tipMarginY = (popupContentSize.height - tangentHeight) * tipPosition.percent
        val y = tangentY - tipMarginY
        return y
    }
}
