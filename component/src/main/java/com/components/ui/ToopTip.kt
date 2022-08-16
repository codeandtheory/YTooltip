package com.components.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import com.components.core.AnchorEdgeView
import com.components.core.EdgePosition
import com.components.core.TooltipStyle
import com.components.core.rememberTooltipStyle
import com.components.utils.*


import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ToolTip(
    modifier: Modifier = Modifier,
    anchorPositionInPixels: MutableState<IntSize>,
    visibleState: MutableState<Boolean>,
    gravity: ToolTipGravity,
    margin: Dp,
    animState: MutableState<ItemPosition>? = null,
    dismissOnTouchOutside: Boolean,
    toolTipStyle: TooltipStyle = rememberTooltipStyle(),
    toolTipContent: @Composable (RowScope) -> Unit
) {
    /**
     * Tooltip states
     */
    val anchorEdgeState = remember { mutableStateOf<AnchorEdgeView>(AnchorEdgeView.Bottom) }
    val tipPosition = remember { EdgePosition() }
    val anchorPosition = remember { EdgePosition() }

    PredictAnchorEdge(
        anchorEdgeState = anchorEdgeState,
        yPosition = anchorPositionInPixels.value.height,
        gravity = gravity
    )

    PredictTipPosition(tipPosition, anchorPositionInPixels.value.width)

    Tooltip(
        anchorEdge = anchorEdgeState.value,
        visible = visibleState.value,
        enterTransition = fadeIn(),
        exitTransition = fadeOut(),
        tipPosition = tipPosition,
        anchorPosition = anchorPosition,
        tooltipStyle = toolTipStyle,
        margin = margin,
        modifier = modifier.clickable(
            remember { MutableInteractionSource() },
            null
        ) {
            // visibleState.value = false
        },
        onDismissRequest = {
            visibleState.value = !dismissOnTouchOutside
            if (animState != null) {
                animState.value = when (animState.value) {
                    ItemPosition.Start -> ItemPosition.Finish
                    ItemPosition.Finish -> ItemPosition.Start
                }
            }
        },
        properties = PopupProperties(clippingEnabled = false),
    ) {
        toolTipContent(this)
    }

}

@ExperimentalAnimationApi
@Composable
fun Tooltip(
    anchorEdge: AnchorEdgeView,
    enterTransition: EnterTransition,
    exitTransition: ExitTransition,
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    tooltipStyle: TooltipStyle = rememberTooltipStyle(),
    tipPosition: EdgePosition = remember { EdgePosition() },
    anchorPosition: EdgePosition = remember { EdgePosition() },
    margin: Dp = DEFAULT_MARGIN,
    onDismissRequest: (() -> Unit)? = null,
    properties: PopupProperties = remember { PopupProperties() },
    content: @Composable RowScope.() -> Unit,
) = with(anchorEdge) {
    val height = LocalContext.current.resources.getStatusBarHeight()
    var transitionState by remember { mutableStateOf(TRANSITION_GONE) }
    LaunchedEffect(visible) {
        if (visible) {
            when (transitionState) {
                TRANSITION_EXIT -> transitionState = TRANSITION_ENTER
                TRANSITION_GONE -> {
                    transitionState = TRANSITION_INITIALIZE
                    delay(1)
                    transitionState = TRANSITION_ENTER
                }
            }
        } else {
            when (transitionState) {
                TRANSITION_INITIALIZE -> transitionState = TRANSITION_GONE
                TRANSITION_ENTER -> transitionState = TRANSITION_EXIT
            }
        }
    }
    if (transitionState != TRANSITION_GONE) {
        Popup(
            popupPositionProvider = TooltipPopupPositionProvider(
                LocalDensity.current,
                anchorEdge,
                tooltipStyle,
                tipPosition,
                anchorPosition,
                margin,
                height
            ),
            onDismissRequest = onDismissRequest,
            properties = properties
        ) {
            if (transitionState == TRANSITION_INITIALIZE) {
                TooltipImpl(
                    tooltipStyle = tooltipStyle,
                    tipPosition = tipPosition,
                    anchorEdge = anchorEdge,
                    modifier = modifier.alpha(0f),
                    content = content,
                )
            }
            AnimatedVisibility(
                visible = transitionState == TRANSITION_ENTER,
                enter = enterTransition,
                exit = exitTransition
            ) {
                remember {
                    object : RememberObserver {
                        override fun onAbandoned() {
                            transitionState = TRANSITION_GONE
                        }

                        override fun onForgotten() {
                            transitionState = TRANSITION_GONE
                        }

                        override fun onRemembered() {
                        }
                    }
                }
                TooltipImpl(
                    modifier = modifier,
                    tooltipStyle = tooltipStyle,
                    tipPosition = tipPosition,
                    anchorEdge = anchorEdge,
                    content = content
                )
            }
        }

    }
}

@Composable
internal fun Tip(anchorEdge: AnchorEdgeView, tooltipStyle: TooltipStyle) = with(anchorEdge) {
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

@Composable
internal fun TooltipContentContainer(
    anchorEdge: AnchorEdgeView,
    tooltipStyle: TooltipStyle,
    content: @Composable (RowScope.() -> Unit)
) = with(anchorEdge) {
    Row(
        modifier = Modifier.Companion
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