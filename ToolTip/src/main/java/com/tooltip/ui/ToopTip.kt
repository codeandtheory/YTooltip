package com.tooltip.ui

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
import com.tooltip.core.TooltipStyle
import com.tooltip.core.rememberTooltipStyle
import com.tooltip.core.ToolTipAnchorEdgeView
import com.tooltip.core.ToolTipHorizontalEdge
import com.tooltip.core.ToolTipEdgePosition
import com.tooltip.utils.ItemPosition
import com.tooltip.utils.ToolTipGravity
import com.tooltip.utils.PredictAnchorEdge
import com.tooltip.utils.PredictTipPosition
import com.tooltip.utils.DEFAULT_MARGIN
import com.tooltip.utils.TRANSITION_GONE
import com.tooltip.utils.TRANSITION_INITIALIZE
import com.tooltip.utils.TRANSITION_EXIT
import com.tooltip.utils.TRANSITION_ENTER
import com.tooltip.utils.getStatusBarHeight
import com.tooltip.utils.TooltipPopupPositionProvider
import com.tooltip.utils.TooltipImpl
import kotlinx.coroutines.delay

/**
 * Component that allow you to show ToolTip at specific anchorPosition with custom/default content.
 * <p>
 * ToolTip Composable function will return @Composable (RowScope).
 * @Composable (RowScope) contains ToolTip's custom/default content.
 * if @param toolTipContent is used to pass custom/default toolTipContent.
 * if @param onDismissRequest is used to dismiss ToolTip.
 *
 *
 * @param modifier Custom modifier.
 * @param anchorPositionInPixels Anchor position in pixel from root.
 * @param visibleState Visibility state.
 * @param gravity Gravity of ToolTip.
 * @param margin Margin of ToolTip.
 * @param animState Animation state for Animation.
 * @param dismissOnTouchOutside Dismiss when clicking outside.
 * @param toolTipStyle ToolTip custom style.
 * @param toolTipContent ToolTip custom content.
 */
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
    val anchorEdgeState =
        remember { mutableStateOf<ToolTipAnchorEdgeView>(ToolTipHorizontalEdge.Bottom) }
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
            if (dismissOnTouchOutside) {
                visibleState.value = !dismissOnTouchOutside
                if (animState != null) {
                    animState.value = when (animState.value) {
                        ItemPosition.Start -> ItemPosition.Finish
                        ItemPosition.Finish -> ItemPosition.Start
                    }
                }
            }
        },
        properties = PopupProperties(clippingEnabled = false),
    ) {
        toolTipContent(this)
    }

}

/**
 * Component that allow you to show ToolTip at specific anchorPosition with custom/default content.
 * <p>
 * ToolTip Composable function will return @Composable (RowScope).
 * @Composable (RowScope) contains ToolTip's custom/default content.
 * if @param content is used to pass custom/default toolTipContent.
 * if @param onDismissRequest is used to dismiss ToolTip.
 *
 *
 * @param anchorEdge Anchor edge.
 * @param enterTransition Enter animation transition.
 * @param exitTransition Exit animation transition.
 * @param modifier Custom modifier.
 * @param visible ToolTip visibility.
 * @param tooltipStyle Custom ToolTip style.
 * @param tipPosition ToolTip position.
 * @param anchorPosition Anchor position.
 * @param margin Margin.
 * @param onDismissRequest Dismiss callback.
 * @param properties Popup properties.
 * @param content ToolTip custom content.
 */
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


