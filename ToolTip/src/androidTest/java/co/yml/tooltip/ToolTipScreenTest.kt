package co.yml.tooltip

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
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import co.yml.tooltip.ui.ToolTipScreen
import co.yml.tooltip.utils.EMPTY_STRING
import org.junit.Rule
import org.junit.Test

class ToolTipScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val visibleHintCoordinates: MutableState<LayoutCoordinates?> =   mutableStateOf(null)
    private val isHintVisible: MutableState<Boolean> = mutableStateOf(false)

    @Test
    fun whenAnyToolTipIsVisibleToolTipScreenWillVisible() {
        composeTestRule.setContent {
            ToolTipScreen(
                modifier = Modifier.semantics {
                    testTag = "toolTipScreen"
                },
                paddingHighlightArea = 0f,
                mainContent = {
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
                },
                anyHintVisible = !isHintVisible.value,
                visibleHintCoordinates = visibleHintCoordinates,
            )
        }
        composeTestRule.onNodeWithTag("toolTipScreen").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("icon").performClick()
        composeTestRule.onNodeWithContentDescription("tipView").assertIsDisplayed()
    }

    @Test
    fun whenAnyToolTipIsNotVisibleToolTipScreenWillBeHidden() {
        composeTestRule.setContent {
            ToolTipScreen(
                modifier = Modifier.semantics {
                    testTag = "toolTipScreen"
                },
                paddingHighlightArea = 0f,
                mainContent = {
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
                },
                anyHintVisible = isHintVisible.value,
                visibleHintCoordinates = visibleHintCoordinates,
            )
        }
        composeTestRule.onNodeWithTag("toolTipScreen").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("icon").performClick()
        composeTestRule.onNodeWithContentDescription("tipView").assertIsDisplayed()
    }
}