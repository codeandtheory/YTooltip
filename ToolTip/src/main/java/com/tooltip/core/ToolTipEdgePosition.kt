package com.tooltip.core

import androidx.annotation.FloatRange
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Specifies a position on an ToolTipEdge.
 */
class ToolTipEdgePosition(
    @FloatRange(from = 0.0, to = 1.0)
    toolTipEdgePercent: Float = 0.5f,
    toolTipOffset: Dp = 0.dp
) {
    /**
     * When it comes to either [ToolTipHorizontalEdge.Top] or [ToolTipHorizontalEdge.Bottom],
     * toolTipPercent 0.0 means the horizontal start position of the anchor,
     * and toolTipPercent 1.0 means the horizontal end position of the anchor.
     *
     * If it comes to either [ToolTipVerticalEdge.Start] or [ToolTipVerticalEdge.End],
     * toolTipPercent 0.0 means the top of the anchor, and toolTipPercent 1.0 means the bottom of the anchor.
     */
    @get:FloatRange(from = 0.0, to = 1.0)
    @setparam:FloatRange(from = 0.0, to = 1.0)
    var toolTipPercent by mutableStateOf(toolTipEdgePercent)

    /**
     * Amount of dps from the percentage position on the edge.
     *
     * For example, if [toolTipPercent] is 0.5 and [toolTipOffset] is 10.dp, tip will point out the location
     * of 10.dp from the center of the edge.
     *
     * This allows negative value.
     */
    var toolTipOffset by mutableStateOf(toolTipOffset)
}