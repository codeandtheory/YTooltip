package com.tooltip.core

import androidx.annotation.FloatRange
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * ToolTipEdgePosition holds the value
 * @param toolTipEdgePercent TipEdgePercentage
 * @param toolTipOffset  TipOffset
 * @param toolTipRectOffset  TipRectOffset
 * */
class ToolTipEdgePosition(
    @FloatRange(from = 0.0, to = 1.0)
    toolTipEdgePercent: Float = 0.5f,
    toolTipOffset: Dp = 0.dp,
    toolTipRectOffset: Offset = Offset.Zero
) {
    @get:FloatRange(from = 0.0, to = 1.0)
    @setparam:FloatRange(from = 0.0, to = 1.0)
    var percent by mutableStateOf(toolTipEdgePercent)

    var offset by mutableStateOf(toolTipOffset)

    var mPositionRect by mutableStateOf(toolTipRectOffset)
}