package co.yml.tooltip.ui

import androidx.compose.runtime.Composable
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
import co.yml.tooltip.core.rememberTooltipStyle
import co.yml.tooltip.utils.CLOSE_STRING
import co.yml.tooltip.utils.DEFAULT_MARGIN
import co.yml.tooltip.utils.DEFAULT_PADDING
import co.yml.tooltip.utils.DEFAULT_SIZE
import co.yml.tooltip.utils.EMPTY_STRING
import co.yml.tooltip.utils.ItemPosition
import co.yml.tooltip.utils.ToolTipGravity
import co.yml.tooltip.utils.calculatePosition

/**
 * Component that allow you to show ToolTip with custom/default content.
 * <p>
 * ToolTipHintView Composable function will return @Composable (BoxScope).
 * @Composable (BoxScope) contains a ToolTip with custom/default content.
 * if @param customHintContent is not null then @ToolTip will return a ToolTip with custom toolTipContent.
 * if @param customHintContent is null then @ToolTip will return a ToolTip with default toolTipContent.
 *
 *
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

