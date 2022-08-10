package com.yml.floatinghint

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.*
import androidx.core.app.ActivityCompat.startActivityForResult
import com.yml.floatinghint.core.rememberTooltipStyle
import com.yml.floatinghint.ui.ToolTip
import com.yml.floatinghint.utils.*


enum class ItemPosition {
    Start, Finish
}

enum class AbsoluteOffset {
    X, Y
}

@Composable
fun FloatingHint(
    modifier: Modifier = Modifier,
    hintText: String = EMPTY_STRING,
    hintTextColor: Color = Color.White,
    hintBackgroundColor: Color = Color.LightGray,
    isHintVisible: MutableState<Boolean>,
    anyHintVisible: MutableState<Boolean>,
    hintGravity: ToolTipGravity = ToolTipGravity.TOP,
    dismissHintText: String = LocalContext.current.resources.getString(R.string.close),
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
    val context = LocalContext.current


    when (hintGravity) {
        ToolTipGravity.START -> {
            iconArrangement = Arrangement.End
            absoluteOffset = AbsoluteOffset.X
            offsetAnim = animateDpAsState(
                if (animState.value == ItemPosition.Start) startAnimStartPos else startAnimEndPos,
                tween(animDurationMillis, easing = cubicBezierEasing)
            ).value
        }
        ToolTipGravity.END -> {
            iconArrangement = Arrangement.Start
            absoluteOffset = AbsoluteOffset.X
            offsetAnim = animateDpAsState(
                if (animState.value == ItemPosition.Start) endAnimStartPos else endAnimEndPos,
                tween(animDurationMillis, easing = cubicBezierEasing)
            ).value
        }
        ToolTipGravity.TOP -> {
            iconArrangement = Arrangement.Center
            absoluteOffset = AbsoluteOffset.Y
            offsetAnim = animateDpAsState(
                if (animState.value == ItemPosition.Start) topAnimStartPos else topAnimEndPos,
                tween(animDurationMillis, easing = cubicBezierEasing)
            ).value
        }
        ToolTipGravity.BOTTOM -> {
            iconArrangement = Arrangement.Center
            absoluteOffset = AbsoluteOffset.Y
            offsetAnim = animateDpAsState(
                if (animState.value == ItemPosition.Start) bottomAnimStartPos else bottomAnimEndPos,
                tween(animDurationMillis, easing = cubicBezierEasing)
            ).value
        }
        else -> {
            iconArrangement = Arrangement.Center
            absoluteOffset = AbsoluteOffset.Y
            offsetAnim = animateDpAsState(
                if (animState.value == ItemPosition.Start) topAnimStartPos else topAnimEndPos,
                tween(animDurationMillis, easing = cubicBezierEasing)
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

        FloatingHintView(
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
            animState = animState,
            anyHintVisible = anyHintVisible
        ) { _, modifier ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .height(circleSize)
                    .width(circleSize)
                    .background(
                        if (isHintVisible.value) Color.White else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable {
                        anyHintVisible.value = !isHintVisible.value
                        isHintVisible.value = !isHintVisible.value
                        animState.value = when (animState.value) {
                            ItemPosition.Start -> ItemPosition.Finish
                            ItemPosition.Finish -> ItemPosition.Start
                        }
                        OverlayService(context) {
                             FloatingHint(
                                 anyHintVisible = anyHintVisible,
                                 isHintVisible = isHintVisible,
                                 hintGravity = hintGravity,
                                 customHintContent = {
                                     customHintContent
                                 },
                                 dismissOnTouchOutside = dismissOnTouchOutside,
                             )
                        }.showOverlay()
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


@Composable
fun FloatingHintView(
    modifier: Modifier = Modifier,
    hintText: String = EMPTY_STRING,
    hintTextColor: Color = Color.White,
    hintBackgroundColor: Color = Color.LightGray,
    isHintVisible: MutableState<Boolean>,
    anyHintVisible: MutableState<Boolean>,
    hintGravity: ToolTipGravity,
    dismissHintText: String = LocalContext.current.resources.getString(R.string.close),
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

    Box() {
        /**
         * state holds the live position of center of anchor
         */
        val anchorPosition = remember {
            mutableStateOf(DEFAULT_SIZE)
        }
        /**
         * Create anchor composable view
         * passing modifier along with the scope lets it gives access to the
         * modifier of the anchor,
         * and its position can be calculated [onGloballyPositioned]
         */
        anchor(this, Modifier.onGloballyPositioned {
            anchorPosition.value = it.calculatePosition()
        })

        /**
         * Tooltip container
         *
         * Content of the tooltip is Text
         */
        ToolTip(
            modifier = modifier,
            anchorPositionInPixels = anchorPosition,
            visibleState = isHintVisible,
            anyHintVisible = anyHintVisible,
            gravity = hintGravity,
            toolTipStyle = toolTipStyle,
            dismissOnTouchOutside = dismissOnTouchOutside,
            margin = margin,
            animState = animState,
            toolTipContent = customHintContent ?: {
                DefaultHintView(
                    hintText = hintText,
                    animState = animState,
                    hintTextColor = hintTextColor,
                    dismissHintTextColor = dismissHintTextColor,
                    isHintVisible = isHintVisible,
                    anyHintVisible = anyHintVisible,
                    dismissHintText = dismissHintText,
                    isDismissButtonHide = isDismissButtonHide,
                )
            }
        )
    }
}


@Composable
fun DefaultHintView(
    hintText: String = EMPTY_STRING,
    hintTextColor: Color = Color.White,
    isHintVisible: MutableState<Boolean>,
    dismissHintText: String = LocalContext.current.resources.getString(R.string.close),
    dismissHintTextColor: Color = Color.White,
    isDismissButtonHide: Boolean = false,
    anyHintVisible: MutableState<Boolean>,
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
                        anyHintVisible.value = !isHintVisible.value
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
