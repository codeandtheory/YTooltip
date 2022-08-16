package com.components.utils

import android.content.res.Resources
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.components.core.AnchorEdgeView
import com.components.core.EdgePosition
import com.components.core.TooltipStyle
import com.components.ui.Tip
import com.components.ui.TooltipContentContainer



fun LayoutCoordinates.calculatePosition(): IntSize {
    val start = positionInWindow().x
    val width = size.width

    val top = positionInWindow().y
    val height = size.height

    return IntSize(
        width = (start + width / 2).toInt(),
        height = (top + height / 2).toInt()
    )
}

@Composable
fun PredictAnchorEdge(
    anchorEdgeState: MutableState<AnchorEdgeView>,
    yPosition: Int,
    gravity: ToolTipGravity = ToolTipGravity.NONE,
    maxHeightOfTooltip: Dp = TOOLTIP_MAX_HEIGHT
) {
    val screenHeightDp = LocalConfiguration.current.screenWidthDp.dp
    val verticalCenterDp = screenHeightDp / 2

    with(LocalDensity.current) {

        if (gravity == ToolTipGravity.NONE) {
            val anchorYPosDp = yPosition.toDp()

            if (anchorYPosDp >= verticalCenterDp) {
                /**
                 * Anchor Lies in the bottom half of the screen
                 * Check if enough space is available for tool tip
                 * If not show tool tip on the top
                 */
                val spaceBelowAnchor = screenHeightDp - anchorYPosDp - TOOLTIP_ADDITIONAL_PADDING
                if (spaceBelowAnchor > maxHeightOfTooltip) {
                    anchorEdgeState.value = AnchorEdgeView.Bottom
                } else {
                    /**
                     * There is no enough space, show tooltip at the TOP
                     */
                    anchorEdgeState.value = AnchorEdgeView.Top
                }
            } else {
                /**
                 * Anchor lies in the top half od the screen.
                 * Tool tip can be shown below anchor
                 */
                anchorEdgeState.value = AnchorEdgeView.Bottom
            }
        } else {
            when (gravity) {
                ToolTipGravity.TOP -> anchorEdgeState.value = AnchorEdgeView.Top
                ToolTipGravity.BOTTOM -> anchorEdgeState.value = AnchorEdgeView.Bottom
                ToolTipGravity.START -> anchorEdgeState.value = AnchorEdgeView.Start
                ToolTipGravity.END -> anchorEdgeState.value = AnchorEdgeView.End
                else -> {}
            }
        }
    }
}

@Composable
fun PredictTipPosition(
    tipPosition: EdgePosition,
    xPosition: Int,
    maxWidthPercent: Float = TOOLTIP_MAX_WIDTH_PERCENT
) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val horizontalCenterDp = screenWidthDp / 2

    val maxWidthOfTooltip = screenWidthDp * maxWidthPercent

    with(LocalDensity.current) {
        // Decide Tip position
        val anchorPosInDp = xPosition.toDp()

        if (anchorPosInDp >= horizontalCenterDp) {
            /**
             * Anchor is present in the right part of the screen
             */
            val spaceToRightOfAnchor = screenWidthDp - anchorPosInDp - TOOLTIP_ADDITIONAL_PADDING
            if (spaceToRightOfAnchor > maxWidthOfTooltip / 2) {
                // Tooltip can be shown with tip position at the center of the tooltip width
                tipPosition.percent = TIP_POS_PERCENT_CENTER
            } else {
                tipPosition.percent = anchorPosInDp / screenWidthDp
            }

        } else {
            /**
             * Anchor is present in the left part of the screen
             */
            val spaceToLeftOfAnchor = anchorPosInDp - TOOLTIP_ADDITIONAL_PADDING

            if (spaceToLeftOfAnchor > maxWidthOfTooltip / 2) {
                tipPosition.percent = TIP_POS_PERCENT_CENTER
            } else {
                tipPosition.percent = anchorPosInDp / screenWidthDp
            }
        }
    }
}


fun Resources.getStatusBarHeight(): Int {
    var statusBarHeight = 0
    val resourceId = getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        statusBarHeight = getDimensionPixelSize(resourceId)
    }
    return statusBarHeight
}

/**
 * @param tooltipStyle Custom style tooltip
 * @param tipPosition Tooltip position.
 * @param anchorEdge AnchorEdge for tip.
 * @param modifier Custom modifier.
 * @param content Custom content.
 */
@Composable
fun AnchorEdgeView.TooltipImpl(
    tooltipStyle: TooltipStyle,
    tipPosition: EdgePosition,
    anchorEdge: AnchorEdgeView,
    modifier: Modifier = Modifier,
    content: @Composable (RowScope.() -> Unit)
) {
    AnchorEdgeViewTooltipContainer(
        modifier = modifier,
        cornerRadius = tooltipStyle.cornerRadius,
        tipPosition = tipPosition,
        tip = { Tip(anchorEdge, tooltipStyle) },
        content = {
            TooltipContentContainer(
                anchorEdge = anchorEdge,
                tooltipStyle = tooltipStyle,
                content = content
            )
        }
    )
}