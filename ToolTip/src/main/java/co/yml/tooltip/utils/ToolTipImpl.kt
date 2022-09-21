package co.yml.tooltip.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import co.yml.tooltip.core.ToolTipAnchorEdgeView
import co.yml.tooltip.core.ToolTipEdgePosition
import co.yml.tooltip.core.TooltipStyle


/**
 * Component that allow you to show ToolTip at specific tipPosition with content.
 * This component is implementation of ToolTip.
 *
 * @param tooltipStyle ToolTip style/
 * @param tipPosition ToolTip position.
 * @param anchorEdge Anchor edge view.
 * @param modifier Custom modifier.
 * @param content Compose content.
 */
@Composable
fun ToolTipAnchorEdgeView.TooltipImpl(
    tooltipStyle: TooltipStyle,
    tipPosition: ToolTipEdgePosition,
    anchorEdge: ToolTipAnchorEdgeView,
    modifier: Modifier = Modifier,
    content: @Composable (RowScope.() -> Unit)
) {
    anchorEdgeViewTooltipContainer(
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

/**
 * Component that allow you to create a ToolTip content container with ToolTip content.
 * This component will return a ToolTip content container.
 * We can set tooltipStyle for ToolTip.
 *
 *
 * @param anchorEdge Anchor edge view.
 * @param tooltipStyle ToolTip style.
 * @param content Compose content.
 */
@Composable
fun TooltipContentContainer(
    anchorEdge: ToolTipAnchorEdgeView,
    tooltipStyle: TooltipStyle,
    content: @Composable (RowScope.() -> Unit)
) = with(anchorEdge) {
    Row(
        modifier = Modifier
            .minSize(tooltipStyle)
            .background(
                color = tooltipStyle.color,
                shape = RoundedCornerShape(tooltipStyle.cornerRadius)
            )
            .padding(tooltipStyle.contentPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColorFor(tooltipStyle.color)
        ) {
            content()
        }
    }
}

/**
 * Component that allow you to create a Tip.
 * This component is used to draw shape for ToolTip.
 * We can set tooltipStyle for Tip.
 *
 *
 * @param anchorEdge Anchor edge view
 * @param tooltipStyle ToolTip style.
 */
@Composable
fun Tip(anchorEdge: ToolTipAnchorEdgeView, tooltipStyle: TooltipStyle) = with(anchorEdge) {
    Box(modifier = Modifier
        .size(
            width = anchorEdge.selectWidth(
                tooltipStyle.tipWidth,
                tooltipStyle.tipHeight
            ),
            height = anchorEdge.selectHeight(
                tooltipStyle.tipWidth,
                tooltipStyle.tipHeight
            )
        )
        .background(
            color = tooltipStyle.color,
            shape = GenericShape { size, layoutDirection ->
                this.drawTip(size, layoutDirection)
            }
        )
    )
}