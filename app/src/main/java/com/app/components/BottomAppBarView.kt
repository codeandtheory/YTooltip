package com.app.components

import android.os.Handler
import android.os.Looper
import com.yml.tooltip.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.app.constants.DELAY_IN_MILLIS
import com.app.constants.MAX_WIDTH_PER
import com.app.data.remote.AppConfig
import com.app.ui.theme.*
import com.app.ui.theme.DEFAULT_SCREEN_PADDING
import com.tooltip.ToolTipView
import com.tooltip.utils.*

/**
 * @Composable BottomAppBarView is used to create bottom appBar view.
 * @param selectedIndex is current selected index.
 * @param visibleHintCoordinates is used for visible hint coordinates.
 * @param isHintVisibleHome is used to handle visibility of home tab ToolTip.
 * @param isHintVisibleTechnology is used to handle visibility of Category tab ToolTip.
 * @param isHintVisibleScience is used to handle visibility of Settings tab ToolTip.
 */
@Composable
fun BottomAppBarView(
    selectedIndex: MutableState<Int>,
    visibleHintCoordinates: MutableState<LayoutCoordinates?>,
    isHintVisibleHome: MutableState<Boolean>,
    isHintVisibleTechnology: MutableState<Boolean>,
    isHintVisibleScience: MutableState<Boolean>,
) {

    BottomAppBar(
        content = {
            BottomNavigation(
                elevation = DEFAULT_ELEVATION
            ) {
                /**
                 * @Composable ToolTipView is used to show ToolTip on screen.
                 * Here we are doing configuration of ToolTipView.
                 * isHintVisible is used to handle visibility of ToolTip.
                 * customHintContent is custom ToolTip content.
                 * customViewClickable is custom ToolTip clickable content.
                 * onClick is additional click handling.
                 * visibleHintCoordinates is used to find coordinates Hint/ToolTip on screen.
                 * Here ToolTipContentWithIcon contains custom ToolTip content.
                 */
                ToolTipView(
                    visibleHintCoordinates = visibleHintCoordinates,
                    isHintVisible = isHintVisibleHome,
                    dismissOnTouchOutside = true,
                    hintBackgroundColor = ToolTipBg,
                    modifier = Modifier.padding(TOOLTIP_ADDITIONAL_PADDING),
                    customHintContent = {
                        ToolTipContentWithIcon(
                            hintText = "${stringResource(R.string.headlines)} - ${AppConfig.general}",
                            isHintVisible = isHintVisibleHome,
                            onNextClick = {
                                selectedIndex.value = 1
                                isHintVisibleHome.value = !isHintVisibleHome.value
                                Handler(Looper.getMainLooper()).postDelayed({
                                    isHintVisibleTechnology.value = !isHintVisibleTechnology.value
                                }, DELAY_IN_MILLIS)
                            },
                            imageVector = Icons.Filled.Home
                        )
                    },
                    onClick = {
                        selectedIndex.value = 0
                    },
                    customViewClickable = {
                        Icon(
                            Icons.Filled.Home, EMPTY_STRING,
                            tint = Grey,
                            modifier = Modifier.padding(DEFAULT_SCREEN_PADDING),
                        )
                    }
                )
                ToolTipView(
                    modifier = Modifier.padding(TOOLTIP_ADDITIONAL_PADDING),
                    visibleHintCoordinates = visibleHintCoordinates,
                    isHintVisible = isHintVisibleTechnology,
                    hintBackgroundColor = ToolTipBg,
                    dismissOnTouchOutside = true,
                    customHintContent = {
                        ToolTipContentWithIcon(
                            hintText = "${stringResource(R.string.headlines)} - ${AppConfig.technology}",
                            isHintVisible = isHintVisibleTechnology,
                            onNextClick = {
                                selectedIndex.value = 2
                                isHintVisibleTechnology.value = !isHintVisibleTechnology.value
                                Handler(Looper.getMainLooper()).postDelayed({
                                    isHintVisibleScience.value = !isHintVisibleScience.value
                                }, DELAY_IN_MILLIS)
                            },
                            imageVector = Icons.Filled.List
                        )
                    },
                    onClick = {
                        selectedIndex.value = 1
                    },
                    customViewClickable = {
                        Icon(
                            Icons.Filled.List, EMPTY_STRING,
                            tint = Grey,
                            modifier = Modifier.padding(DEFAULT_SCREEN_PADDING),
                        )
                    }
                )

                ToolTipView(
                    modifier = Modifier.padding(TOOLTIP_ADDITIONAL_PADDING),
                    visibleHintCoordinates = visibleHintCoordinates,
                    isHintVisible = isHintVisibleScience,
                    hintBackgroundColor = ToolTipBg,
                    dismissOnTouchOutside = true,
                    customHintContent = {
                        ToolTipContentWithIcon(
                            hintText = "${stringResource(R.string.headlines)} - ${AppConfig.science}",
                            isHintVisible = isHintVisibleScience,
                            imageVector = Icons.Filled.Star
                        )
                    },
                    onClick = {
                        selectedIndex.value = 2
                    },
                    customViewClickable = {
                        Icon(
                            Icons.Filled.Star, EMPTY_STRING,
                            tint = Grey,
                            modifier = Modifier.padding(DEFAULT_SCREEN_PADDING),
                        )
                    }
                )
            }
        }
    )
}

/**
 * @Composable ToolTipContentWithIcon is used to create custom ToolTip content.
 * Here we are doing configuration of ToolTipContentWithIcon.
 * hintText is text to show in ToolTip.
 * isHintVisible is used to handle visibility of ToolTip.
 * onNextClick is used to next click handling.
 * imageVector is used to set icon dynamically.
 */
@Composable
fun ToolTipContentWithIcon(
    hintText: String,
    isHintVisible: MutableState<Boolean>,
    onNextClick: (() -> Unit)? = null,
    imageVector: ImageVector,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp * MAX_WIDTH_PER

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = EMPTY_STRING,
                tint = Color.White,
                modifier = Modifier
                    .padding(horizontal = DEFAULT_PADDING)
            )
            Text(
                text = hintText,
                maxLines = MAX_LINE,
                color = Color.White,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(DEFAULT_PADDING)
                    .widthIn(max = (TOOLTIP_MAX_WIDTH_PERCENT * screenWidth).dp)
            )
            Text(
                text = stringResource(R.string.close),
                color = Color.White,
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(DEFAULT_PADDING)
                    .clickable {
                        isHintVisible.value = !isHintVisible.value
                    }
            )
        }

        if (onNextClick != null)
            Text(
                text = stringResource(R.string.next),
                color = Color.White,
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(DEFAULT_PADDING)
                    .clickable {
                        onNextClick()
                    }
            )
    }
}