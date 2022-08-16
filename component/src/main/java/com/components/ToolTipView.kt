package com.components

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.*
import com.components.core.rememberTooltipStyle
import com.components.ui.ToolTip
import com.components.utils.*

/**
 * @param modifier Custom modifier
 * @param hintText ToolTipHint text
 * @param hintTextColor ToolTipHint text color
 * @param hintBackgroundColor ToolTipHint background color
 * @param isHintVisible ToolTipHint visibility
 * @param visibleHintCoordinates ToolTipHint coordinate visibility
 * @param hintGravity ToolTipHint gravity
 * @param dismissHintText ToolTipHint dismiss text
 * @param dismissHintTextColor ToolTipHint dismiss text color
 * @param isDismissButtonHide ToolTipHint dismiss button visibility
 * @param margin ToolTipHint margin
 * @param verticalPadding ToolTipHint vertical padding
 * @param horizontalPadding ToolTipHint horizontal padding
 * @param dismissOnTouchOutside ToolTipHint dismiss on touch outside
 * @param customHintContent ToolTipHint custom content
 * @param customViewClickable ToolTipHint custom view click
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

/**
 * @param modifier Custom modifier
 * @param hintText ToolTipHint text
 * @param hintTextColor ToolTipHint text color
 * @param hintBackgroundColor ToolTipHint background color
 * @param isHintVisible ToolTipHint visibility
 * @param hintGravity ToolTipHint gravity
 * @param dismissHintText ToolTipHint dismiss text
 * @param dismissHintTextColor ToolTipHint dismiss text color
 * @param isDismissButtonHide ToolTipHint dismiss button visibility
 * @param customHintContent ToolTipHint custom content
 * @param margin ToolTipHint margin
 * @param verticalPadding ToolTipHint vertical padding
 * @param animState ToolTipHint animation state
 * @param horizontalPadding ToolTipHint horizontal padding
 * @param dismissOnTouchOutside ToolTipHint dismiss on touch outside
 * @param anchor ToolTipHint anchor view
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
    isDismissButtonHide: Boolean = false,
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
                DefaultToolTipHintView(
                    hintText = hintText,
                    animState = animState,
                    hintTextColor = hintTextColor,
                    dismissHintTextColor = dismissHintTextColor,
                    isHintVisible = isHintVisible,
                    dismissHintText = dismissHintText,
                    isDismissButtonHide = isDismissButtonHide,
                )
            }
        )
    }
}

/**
 * @param hintText Hint text,
 * @param hintTextColor Hint text color,
 * @param isHintVisible Hint visibility ,
 * @param dismissHintText Dismiss text ,
 * @param dismissHintTextColor Dismiss text color,
 * @param isDismissButtonHide Dismiss button visibility,
 * @param anyHintVisible Hint visible,
 * @param animState Animation state,
 * */
@Composable
fun DefaultToolTipHintView(
    hintText: String = EMPTY_STRING,
    hintTextColor: Color = Color.White,
    isHintVisible: MutableState<Boolean>,
    dismissHintText: String = CLOSE_STRING,
    dismissHintTextColor: Color = Color.White,
    isDismissButtonHide: Boolean = false,
    animState: MutableState<ItemPosition>? = null,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = hintText,
            maxLines = MAX_LINE,
            color = hintTextColor,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(DEFAULT_PADDING)
                .widthIn(max = (TOOLTIP_MAX_WIDTH_PERCENT * screenWidth).dp)
        )
        if (!isDismissButtonHide)
            Text(
                text = dismissHintText,
                color = dismissHintTextColor,
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(DEFAULT_PADDING)
                    .clickable {
                        isHintVisible.value = !isHintVisible.value
                        if (animState != null) {
                            animState.value = when (animState.value) {
                                ItemPosition.Start -> ItemPosition.Finish
                                ItemPosition.Finish -> ItemPosition.Start
                            }
                        }
                    }
            )
    }
}
