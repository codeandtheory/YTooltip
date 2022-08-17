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
import androidx.constraintlayout.compose.ConstrainScope
import androidx.constraintlayout.compose.ConstrainedLayoutReference

abstract class ToolTipHorizontalAnchorEdge : ToolTipAnchorEdgeView() {
    override fun ConstrainScope.align(anchor: ConstrainedLayoutReference, bias: Float) {
        linkTo(anchor.start, anchor.end, bias = bias)
    }

    override fun ConstrainScope.nextTo(anchor: ConstrainedLayoutReference, margin: Dp) {
        start.linkTo(anchor.end, margin)
    }

    override fun ConstrainScope.beforeTo(anchor: ConstrainedLayoutReference, margin: Dp) {
        end.linkTo(anchor.start, margin)
    }

    override fun selectWidth(width: Dp, height: Dp): Dp {
        return max(width, height)
    }

    override fun selectHeight(width: Dp, height: Dp): Dp {
        return min(width, height)
    }

    override fun Modifier.minSize(tooltipStyle: TooltipStyle): Modifier = with(tooltipStyle) {
        return widthIn(min = cornerRadius * 2 + max(tipWidth, tipHeight))
    }

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
                    anchorBounds.width * anchorPosition.percent +
                    anchorPosition.offset.toPx()
        } else {
            anchorBounds.right -
                    anchorBounds.width * anchorPosition.percent -
                    anchorPosition.offset.toPx()
        }
        val tangentWidth = (tooltipStyle.cornerRadius * 2 +
                tipPosition.offset.absoluteValue * 2 +
                max(tooltipStyle.tipWidth, tooltipStyle.tipHeight)).toPx()
        val tangentLeft = contactPointX - tangentWidth / 2
        val tipMarginLeft = (popupContentSize.width - tangentWidth) *
                if (layoutDirection == LayoutDirection.Ltr) {
                    tipPosition.percent
                } else {
                    1f - tipPosition.percent
                }
        val x = tangentLeft - tipMarginLeft
        return x
    }
}
