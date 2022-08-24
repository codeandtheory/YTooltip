package com.tooltip

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.tooltip.utils.EMPTY_STRING
import org.junit.Rule
import org.junit.Test

class ToolTipViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val visibleHintCoordinates: MutableState<LayoutCoordinates?> = mutableStateOf(null)
    private val isHintVisible: MutableState<Boolean> = mutableStateOf(false)

    @Test
    fun toolTipVisibleWithCustomContent() {
        composeTestRule.setContent {
            ToolTipView(
                visibleHintCoordinates = visibleHintCoordinates,
                isHintVisible = isHintVisible,
                dismissOnTouchOutside = true,
                customHintContent = {
                    Text(text = "ToolTip" , modifier = Modifier.semantics {
                        contentDescription = "tipView"
                    })
                },
                customViewClickable = {
                    Icon(
                        Icons.Filled.Home, EMPTY_STRING,
                        modifier = Modifier.padding(20.dp).semantics {
                            contentDescription = "icon"
                        },
                    )
                }
            )
        }
        composeTestRule.onNodeWithContentDescription("icon").performClick()
        composeTestRule.onNodeWithContentDescription("tipView").assertIsDisplayed()
    }
}