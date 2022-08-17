package com.components.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.constraintlayout.compose.ConstrainScope
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import kotlin.math.roundToInt

sealed class ToolTipHorizontalEdge : ToolTipHorizontalAnchorEdge() {
    object Top : ToolTipHorizontalEdge() {
        override fun ConstrainScope.outside(anchor: ConstrainedLayoutReference, margin: Dp) {
            bottom.linkTo(anchor.top, margin)
        }

        override fun Path.drawTip(size: Size, layoutDirection: LayoutDirection) {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width / 2f, size.height)
            lineTo(0f, 0f)
        }

        @Composable
        override fun AnchorEdgeViewTooltipContainer(
            modifier: Modifier,
            cornerRadius: Dp,
            tipPosition: ToolTipEdgePosition,
            tip: @Composable () -> Unit,
            content: @Composable () -> Unit
        ) {
            val tipPositionOffset = tipPosition.offset
            ConstraintLayout(modifier = modifier) {
                val (contentContainer, tipContainer) = createRefs()
                Box(
                    modifier = Modifier
                        .constrainAs(contentContainer) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                        .padding(
                            start = if (tipPositionOffset < 0.dp) tipPositionOffset * -2 else 0.dp,
                            end = if (tipPositionOffset > 0.dp) tipPositionOffset * 2 else 0.dp
                        )
                ) {
                    content()
                }
                val tipPadding = cornerRadius + tipPositionOffset.absoluteValue
                Box(
                    modifier = Modifier
                        .constrainAs(tipContainer) {
                            linkTo(
                                contentContainer.start,
                                contentContainer.end,
                                bias = tipPosition.percent
                            )
                            top.linkTo(contentContainer.bottom)
                        }
                        .padding(start = tipPadding, end = tipPadding)
                ) {
                    tip()
                }
            }
        }

        override fun popupPositionCalculate(
            density: Density,
            tooltipStyle: TooltipStyle,
            tipPosition: ToolTipEdgePosition,
            anchorPosition: ToolTipEdgePosition,
            margin: Dp,
            anchorBounds: IntRect,
            layoutDirection: LayoutDirection,
            popupContentSize: IntSize,
            statusBarHeight: Int
        ): IntOffset = with(density) {
            val x = calculatePopupPositionX(
                density,
                layoutDirection,
                anchorBounds,
                anchorPosition,
                tooltipStyle,
                tipPosition,
                popupContentSize
            )
            val y = anchorBounds.top - margin.toPx() - popupContentSize.height
            return IntOffset(x.roundToInt(), y.roundToInt())
        }
    }
    object Bottom : ToolTipHorizontalEdge() {
        override fun ConstrainScope.outside(anchor: ConstrainedLayoutReference, margin: Dp) {
            top.linkTo(anchor.bottom, margin)
        }

        override fun Path.drawTip(size: Size, layoutDirection: LayoutDirection) {
            moveTo(0f, size.height)
            lineTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
        }

        @Composable
        override fun AnchorEdgeViewTooltipContainer(
            modifier: Modifier,
            cornerRadius: Dp,
            tipPosition: ToolTipEdgePosition,
            tip: @Composable () -> Unit,
            content: @Composable () -> Unit
        ) {
            val tipPositionOffset = tipPosition.offset
            ConstraintLayout(modifier = modifier) {
                val (contentContainer, tipContainer) = createRefs()
                Box(
                    modifier = Modifier
                        .constrainAs(contentContainer) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .padding(
                            start = if (tipPositionOffset < 0.dp) tipPositionOffset * -2 else 0.dp,
                            end = if (tipPositionOffset > 0.dp) tipPositionOffset * 2 else 0.dp
                        )
                ) {
                    content()
                }
                val tipPadding = cornerRadius + tipPositionOffset.absoluteValue
                Box(
                    modifier = Modifier
                        .constrainAs(tipContainer) {
                            linkTo(
                                contentContainer.start,
                                contentContainer.end,
                                bias = tipPosition.percent
                            )
                            bottom.linkTo(contentContainer.top)
                        }
                        .padding(start = tipPadding, end = tipPadding)
                ) {
                    tip()
                }
            }
        }

        override fun popupPositionCalculate(
            density: Density,
            tooltipStyle: TooltipStyle,
            tipPosition: ToolTipEdgePosition,
            anchorPosition: ToolTipEdgePosition,
            margin: Dp,
            anchorBounds: IntRect,
            layoutDirection: LayoutDirection,
            popupContentSize: IntSize,
            statusBarHeight: Int
        ): IntOffset = with(density) {
            val x = calculatePopupPositionX(
                density,
                layoutDirection,
                anchorBounds,
                anchorPosition,
                tooltipStyle,
                tipPosition,
                popupContentSize
            )
            val y = anchorBounds.bottom + margin.toPx()
            return IntOffset(x.roundToInt(), y.roundToInt())
        }
    }
}