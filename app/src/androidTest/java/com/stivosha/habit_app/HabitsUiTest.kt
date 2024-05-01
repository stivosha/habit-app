package com.stivosha.habit_app

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kaspersky.components.composesupport.config.withComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HabitsUiTest : TestCase(
    kaspressoBuilder = Kaspresso.Builder.withComposeSupport()
) {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun simpleUiTest() = run {
        step("Open main screen") {
            onComposeScreen<ComposeMainScreen>(composeTestRule) {
                addButton {
                    assertIsDisplayed()
                    performClick()
                }
            }
        }
        step("Open add screen") {
            onComposeScreen<ComposeAddScreen>(composeTestRule) {
                title {
                    assertIsDisplayed()
                    performTextInput("asasasasasasas")
                }
                description {
                    assertIsDisplayed()
                    performTextInput("a45545454")
                }
                addScreenAddButton {
                    assertIsDisplayed()
                    performClick()
                }
            }
        }
        // Не разобрался как замокать запрос в сеть
    }
}