package com.yml.floatinghint.utils

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

const val MAX_LINE = 3
val DEFAULT_PADDING = 5.dp
val DEFAULT_MARGIN = 5.dp
const val EMPTY_STRING = ""

const val TOOLTIP_MAX_WIDTH_PERCENT = .6f
const val TIP_POS_PERCENT_CENTER = 0.5f

val TOOLTIP_MAX_HEIGHT = 200.dp
val TOOLTIP_ADDITIONAL_PADDING = 10.dp
val DEFAULT_SIZE = IntSize(0, 0)

const val TRANSITION_INITIALIZE = 0
const val TRANSITION_ENTER = 1
const val TRANSITION_EXIT = 2
const val TRANSITION_GONE = 3


//FloatingHint anim config
const val animDurationMillis = 1500
val cubicBezierEasing = CubicBezierEasing(0.4f, 0.0f, 0.8f, 0.8f)
val topAnimStartPos = -(200.dp)
val topAnimEndPos = 0.dp
val bottomAnimStartPos = 200.dp
val bottomAnimEndPos = 0.dp
val startAnimStartPos = -(100.dp)
val startAnimEndPos = 0.dp
val endAnimStartPos = 100.dp
val endAnimEndPos = 0.dp
val circleSize = 60.dp