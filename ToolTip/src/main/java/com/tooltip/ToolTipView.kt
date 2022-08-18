package com.tooltip

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.Dp
import com.tooltip.ui.ToolTipHintView
import com.tooltip.utils.ToolTipGravity
import com.tooltip.utils.AbsoluteOffset
import com.tooltip.utils.ItemPosition
import com.tooltip.utils.EMPTY_STRING
import com.tooltip.utils.CLOSE_STRING
import com.tooltip.utils.DEFAULT_PADDING
import com.tooltip.utils.DEFAULT_MARGIN
import com.tooltip.utils.TOP_ANIM_START_POS
import com.tooltip.utils.TOP_ANIM_END_POS
import com.tooltip.utils.CUBIC_BEZIER_EASING
import com.tooltip.utils.ANIMATION_DURATION
import com.tooltip.utils.START_ANIM_START_POS
import com.tooltip.utils.START_ANIM_END_POS
import com.tooltip.utils.END_ANIM_START_POS
import com.tooltip.utils.END_ANIM_END_POS
import com.tooltip.utils.BOTTOM_ANIM_START_POS
import com.tooltip.utils.BOTTOM_ANIM_END_POS

/**
 * @param modifier Custom modifier.
 * @param hintText ToolTipHint text.
 * @param hintTextColor ToolTipHint text color.
 * @param hintBackgroundColor ToolTipHint background color.
 * @param isHintVisible ToolTipHint visibility.
 * @param visibleHintCoordinates ToolTipHint coordinate visibility.
 * @param hintGravity ToolTipHint gravity.
 * @param dismissHintText ToolTipHint dismiss text.
 * @param dismissHintTextColor ToolTipHint dismiss text color.
 * @param isDismissButtonHide ToolTipHint dismiss button visibility.
 * @param margin ToolTipHint margin.
 * @param verticalPadding ToolTipHint vertical padding.
 * @param horizontalPadding ToolTipHint horizontal padding.
 * @param dismissOnTouchOutside ToolTipHint dismiss on touch outside.
 * @param customHintContent ToolTipHint custom content.
 * @param customViewClickable ToolTipHint custom view click.
 */

@Composable
fun ToolTipView(
    modifier: Modifier = Modifier,
    hintText: String = EMPTY_STRING,
    hintTextColor: Color = Color.White,
    hintBackgroundColor: Color = Color.LightGray,
    isHintVisible: MutableState<Boolean>,
    visibleHintCoordinates: MutableState<LayoutCoordinates?>,
    hintGravity: ToolTipGravity = ToolTipGravity.NONE,
    dismissHintText: String = CLOSE_STRING,
    dismissHintTextColor: Color = Color.White,
    isDismissButtonHide: Boolean = false,
    margin: Dp = DEFAULT_MARGIN,
    verticalPadding: Dp = DEFAULT_PADDING,
    horizontalPadding: Dp = DEFAULT_PADDING,
    dismissOnTouchOutside: Boolean = false,
    customHintContent: (@Composable (RowScope) -> Unit)? = null,
    customViewClickable: (@Composable () -> Unit)? = null,
) {
    val iconArrangement: Arrangement.Horizontal
    val animState = remember { mutableStateOf(ItemPosition.Start) }
    val offsetAnim: Dp
    val absoluteOffset: AbsoluteOffset

    when (hintGravity) {
        ToolTipGravity.START -> {
            iconArrangement = Arrangement.End
            absoluteOffset = AbsoluteOffset.X
            offsetAnim = animateDpAsState(
                if (animState.value == ItemPosition.Start) START_ANIM_START_POS else START_ANIM_END_POS,
                tween(ANIMATION_DURATION, easing = CUBIC_BEZIER_EASING)
            ).value
        }
        ToolTipGravity.END -> {
            iconArrangement = Arrangement.Start
            absoluteOffset = AbsoluteOffset.X
            offsetAnim = animateDpAsState(
                if (animState.value == ItemPosition.Start) END_ANIM_START_POS else END_ANIM_END_POS,
                tween(ANIMATION_DURATION, easing = CUBIC_BEZIER_EASING)
            ).value
        }
        ToolTipGravity.TOP -> {
            iconArrangement = Arrangement.Center
            absoluteOffset = AbsoluteOffset.Y
            offsetAnim = animateDpAsState(
                if (animState.value == ItemPosition.Start) TOP_ANIM_START_POS else TOP_ANIM_END_POS,
                tween(ANIMATION_DURATION, easing = CUBIC_BEZIER_EASING)
            ).value
        }
        ToolTipGravity.BOTTOM -> {
            iconArrangement = Arrangement.Center
            absoluteOffset = AbsoluteOffset.Y
            offsetAnim = animateDpAsState(
                if (animState.value == ItemPosition.Start) BOTTOM_ANIM_START_POS else BOTTOM_ANIM_END_POS,
                tween(ANIMATION_DURATION, easing = CUBIC_BEZIER_EASING)
            ).value
        }
        else -> {
            iconArrangement = Arrangement.Center
            absoluteOffset = AbsoluteOffset.Y
            offsetAnim = animateDpAsState(
                if (animState.value == ItemPosition.Start) TOP_ANIM_START_POS else TOP_ANIM_END_POS,
                tween(ANIMATION_DURATION, easing = CUBIC_BEZIER_EASING)
            ).value
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = iconArrangement
    ) {
        var modifierNew = modifier

        if (customHintContent == null) {
            modifierNew = if (absoluteOffset == AbsoluteOffset.Y) {
                modifier.absoluteOffset(y = offsetAnim)
            } else {
                modifier.absoluteOffset(x = offsetAnim)
            }
        }

        ToolTipHintView(
            modifier = modifierNew,
            hintText = hintText,
            hintTextColor = hintTextColor,
            hintBackgroundColor = hintBackgroundColor,
            dismissHintTextColor = dismissHintTextColor,
            dismissHintText = dismissHintText,
            isHintVisible = isHintVisible,
            hintGravity = hintGravity,
            customHintContent = customHintContent,
            isDismissButtonHide = isDismissButtonHide,
            margin = margin,
            verticalPadding = verticalPadding,
            horizontalPadding = horizontalPadding,
            dismissOnTouchOutside = dismissOnTouchOutside,
            animState = animState
        ) { _, modifier ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .onGloballyPositioned {
                        Log.d(
                            "onGloballyPositioned",
                            it
                                .positionInRoot()
                                .toString()
                        )
                        Log.d("onGloballyPositioned", it.size.toString())
                        visibleHintCoordinates.value = it
                    }
                    .clickable {
                        isHintVisible.value = !isHintVisible.value
                        animState.value = when (animState.value) {
                            ItemPosition.Start -> ItemPosition.Finish
                            ItemPosition.Finish -> ItemPosition.Start
                        }
                    }) {
                if (customViewClickable != null) {
                    customViewClickable()
                } else {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = EMPTY_STRING,
                        tint = Color.Black,
                    )
                }
            }
        }
    }
}

