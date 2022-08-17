package com.components.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.RememberObserver
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.Popup
import com.components.core.TooltipStyle
import com.components.core.rememberTooltipStyle
import com.components.core.ToolTipAnchorEdgeView
import com.components.core.ToolTipHorizontalEdge
import com.components.core.ToolTipEdgePosition
import com.components.utils.ItemPosition
import com.components.utils.ToolTipGravity
import com.components.utils.PredictAnchorEdge
import com.components.utils.PredictTipPosition
import com.components.utils.DEFAULT_MARGIN
import com.components.utils.TRANSITION_GONE
import com.components.utils.TRANSITION_INITIALIZE
import com.components.utils.TRANSITION_EXIT
import com.components.utils.TRANSITION_ENTER
import com.components.utils.getStatusBarHeight
import com.components.utils.TooltipPopupPositionProvider
import com.components.utils.TooltipImpl
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
    val anchorEdgeState = remember { mutableStateOf<ToolTipAnchorEdgeView>(ToolTipHorizontalEdge.Bottom) }
    val tipPosition = remember { ToolTipEdgePosition() }
    val anchorPosition = remember { ToolTipEdgePosition() }

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
    anchorEdge: ToolTipAnchorEdgeView,
    enterTransition: EnterTransition,
    exitTransition: ExitTransition,
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    tooltipStyle: TooltipStyle = rememberTooltipStyle(),
    tipPosition: ToolTipEdgePosition = remember { ToolTipEdgePosition() },
    anchorPosition: ToolTipEdgePosition = remember { ToolTipEdgePosition() },
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


