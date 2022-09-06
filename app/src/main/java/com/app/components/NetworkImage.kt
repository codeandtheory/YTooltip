package com.app.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.app.constants.DEFAULT_ROUNDED_CORNER_PER
import com.app.constants.EMPTY_STRING
import com.app.ui.theme.DEFAULT_BANNER_HEIGHT
import com.app.ui.theme.DEFAULT_BORDER_WIDTH
import com.app.ui.theme.DEFAULT_INNER_PADDING
import com.app.ui.theme.Teal200
import com.google.accompanist.glide.rememberGlidePainter

@Composable
fun NetworkImageBanner(url: String) {
    Image(
        painter = rememberGlidePainter(url),
        modifier = Modifier
            .height(DEFAULT_BANNER_HEIGHT)
            .clip(RoundedCornerShape(DEFAULT_ROUNDED_CORNER_PER))
            .background(Teal200)
            .fillMaxWidth(),
        contentScale = ContentScale.FillBounds,
        contentDescription = EMPTY_STRING,
    )
}