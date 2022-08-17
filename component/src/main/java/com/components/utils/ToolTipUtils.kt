package com.components.utils

import android.content.res.Resources
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.IntSize

fun LayoutCoordinates.calculatePosition(): IntSize {
    val start = positionInWindow().x
    val width = size.width

    val top = positionInWindow().y
    val height = size.height

    return IntSize(
        width = (start + width / 2).toInt(),
        height = (top + height / 2).toInt()
    )
}

fun Resources.getStatusBarHeight(): Int {
    var statusBarHeight = 0
    val resourceId = getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        statusBarHeight = getDimensionPixelSize(resourceId)
    }
    return statusBarHeight
}

