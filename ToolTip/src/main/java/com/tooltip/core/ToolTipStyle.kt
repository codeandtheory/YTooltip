package com.tooltip.core

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @param hintText Hint text,
 * @param hintTextColor Hint text color,
 * @param isHintVisible Hint visibility ,
 * @param dismissHintText Dismiss text ,
 * @param dismissHintTextColor Dismiss text color,
 * @param isDismissButtonHide Dismiss button visibility,
 * @param animState Animation state,
 * */

class TooltipStyle internal constructor(
    color: Color,
    cornerRadius: Dp,
    tipWidth: Dp,
    tipHeight: Dp,
    contentPadding: PaddingValues
) {
    var color by mutableStateOf(color)

    var cornerRadius by mutableStateOf(cornerRadius)

    var tipWidth by mutableStateOf(tipWidth)

    var tipHeight by mutableStateOf(tipHeight)

    var contentPadding by mutableStateOf(contentPadding)
}

/**
 * @param color ToolTip color.
 * @param cornerRadius ToolTip corner radius.
 * @param tipWidth ToolTip width.
 * @param tipHeight ToolTip height.
 * @param contentPadding Content padding inside ToolTip.
 * */
@Composable
fun rememberTooltipStyle(
    color: Color = MaterialTheme.colors.secondary,
    cornerRadius: Dp = 8.dp,
    tipWidth: Dp = 24.dp,
    tipHeight: Dp = 8.dp,
    contentPadding: PaddingValues = PaddingValues(12.dp),
): TooltipStyle {
    return remember { TooltipStyle(color, cornerRadius, tipWidth, tipHeight, contentPadding) }
}