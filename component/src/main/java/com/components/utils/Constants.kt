package com.components.utils

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

val DEFAULT_PADDING = 5.dp
val DEFAULT_MARGIN = 5.dp

const val MAX_LINE = 3
const val EMPTY_STRING = ""
const val CLOSE_STRING = "Close"

const val TOOLTIP_MAX_WIDTH_PERCENT = .6f
const val TIP_POS_PERCENT_CENTER = 0.5f

val TOOLTIP_MAX_HEIGHT = 200.dp
val TOOLTIP_ADDITIONAL_PADDING = 10.dp
val DEFAULT_SIZE = IntSize(0, 0)

const val TRANSITION_INITIALIZE = 0
const val TRANSITION_ENTER = 1
const val TRANSITION_EXIT = 2
const val TRANSITION_GONE = 3

const val TRANSPARENT_ALPHA = 0.5f
const val SCREEN_ALPHA = .99f


//FloatingHint anim config
const val ANIMATION_DURATION = 1500

val CUBIC_BEZIER_EASING = CubicBezierEasing(0.4f, 0.0f, 0.8f, 0.8f)
val TOP_ANIM_START_POS = -(200.dp)
val TOP_ANIM_END_POS = 0.dp
val BOTTOM_ANIM_START_POS = 200.dp
val BOTTOM_ANIM_END_POS = 0.dp
val START_ANIM_START_POS = -(100.dp)
val START_ANIM_END_POS = 0.dp
val END_ANIM_START_POS = 100.dp
val END_ANIM_END_POS = 0.dp

const val DEFAULT_CORNER_RADIUS = 20f
const val DEFAULT_SCREEN_PADDING = 20f