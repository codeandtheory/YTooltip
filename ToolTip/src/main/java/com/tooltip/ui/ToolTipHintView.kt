package com.tooltip.ui

import androidx.compose.runtime.Composable
import com.tooltip.utils.EMPTY_STRING
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import com.tooltip.core.rememberTooltipStyle
import com.tooltip.utils.ItemPosition
import com.tooltip.utils.ToolTipGravity
import com.tooltip.utils.CLOSE_STRING
import com.tooltip.utils.DEFAULT_PADDING
import com.tooltip.utils.DEFAULT_MARGIN
import com.tooltip.utils.DEFAULT_SIZE
import com.tooltip.utils.calculatePosition

/**
 * @param modifier Custom modifier.
 * @param hintText ToolTipHint text.
 * @param hintTextColor ToolTipHint text color.
 * @param hintBackgroundColor ToolTipHint background color.
 * @param isHintVisible ToolTipHint visibility.
 * @param hintGravity ToolTipHint gravity.
 * @param dismissHintText ToolTipHint dismiss text.
 * @param dismissHintTextColor ToolTipHint dismiss text color.
 * @param isDismissButtonHidden ToolTipHint dismiss button visibility.
 * @param customHintContent ToolTipHint custom content.
 * @param margin ToolTipHint margin.
 * @param verticalPadding ToolTipHint vertical padding.
 * @param animState ToolTipHint animation state.
 * @param horizontalPadding ToolTipHint horizontal padding.
 * @param dismissOnTouchOutside ToolTipHint dismiss on touch outside.
 * @param anchor ToolTipHint anchor view.
 */
@Composable
fun ToolTipHintView(
    modifier: Modifier = Modifier,
    hintText: String = EMPTY_STRING,
    hintTextColor: Color = Color.White,
    hintBackgroundColor: Color = Color.LightGray,
    isHintVisible: MutableState<Boolean>,
    hintGravity: ToolTipGravity,
    dismissHintText: String = CLOSE_STRING,
    dismissHintTextColor: Color = Color.White,
    isDismissButtonHidden: Boolean = false,
    customHintContent: (@Composable (RowScope) -> Unit)? = null,
    margin: Dp = DEFAULT_MARGIN,
    verticalPadding: Dp = DEFAULT_PADDING,
    animState: MutableState<ItemPosition>? = null,
    horizontalPadding: Dp = DEFAULT_PADDING,
    dismissOnTouchOutside: Boolean = false,
    anchor: @Composable (
        scope: BoxScope,
        modifier: Modifier
    ) -> Unit
) {
    /**
     * tooltip style to configure the look of the tooltip
     */
    val toolTipStyle = rememberTooltipStyle().apply {
        contentPadding = PaddingValues(
            horizontal = horizontalPadding,
            vertical = verticalPadding
        )
        color = hintBackgroundColor
    }

    Box {
        /**
         * state holds the live position of center of anchor
         */
        val anchorPosition = remember {
            mutableStateOf(DEFAULT_SIZE)
        }

        anchor(this, Modifier.onGloballyPositioned {
            anchorPosition.value = it.calculatePosition()
        })

        ToolTip(
            modifier = modifier,
            anchorPositionInPixels = anchorPosition,
            visibleState = isHintVisible,
            gravity = hintGravity,
            toolTipStyle = toolTipStyle,
            dismissOnTouchOutside = dismissOnTouchOutside,
            margin = margin,
            animState = animState,
            toolTipContent = customHintContent ?: {
                DefaultToolTipView(
                    hintText = hintText,
                    animState = animState,
                    hintTextColor = hintTextColor,
                    dismissHintTextColor = dismissHintTextColor,
                    isHintVisible = isHintVisible,
                    dismissHintText = dismissHintText,
                    isDismissButtonHidden = isDismissButtonHidden,
                )
            }
        )
    }
}

