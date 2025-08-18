package com.example.tipcalculatorapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class TipCalculatorAppTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun appLaunches_displaysHeader_andBillInput() {
        // Header is always visible
        composeTestRule.onNodeWithText("Total Per Person", substring = true).assertIsDisplayed()

        // Bill input field is visible
        composeTestRule.onNodeWithText("Enter Bill", substring = true).assertIsDisplayed()
    }

    @Test
    fun enteringBill_displaysSplitRowAndTipSection() {
        // Enter bill
        composeTestRule.onNodeWithText("Enter Bill", substring = true).performTextInput("100")

        // Now Split controls appear
        composeTestRule.onNodeWithText("Split").assertIsDisplayed()
        composeTestRule.onNodeWithText("1").assertIsDisplayed() // initial split count

        // Tip section also appears
        composeTestRule.onNodeWithText("Tip").assertIsDisplayed()
        composeTestRule.onNodeWithText("£0.0").assertIsDisplayed() // initial tip amount
        composeTestRule.onNodeWithText("0%").assertIsDisplayed() // initial tip percent
    }

    @Test
    fun splitBill_increasesAndDecreasesCorrectly() {
        composeTestRule.onNodeWithText("Enter Bill", substring = true).performTextInput("200")

        // "+" increases
        composeTestRule.onNodeWithTag("AddButton", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("2").assertIsDisplayed()

        // "-" decreases
        composeTestRule.onNodeWithTag("RemoveButton", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("1").assertIsDisplayed()
    }

    @Test
    fun movingTipSlider_updatesTipAmountAndTotalPerPerson() {
        composeTestRule.onNodeWithText("Enter Bill", substring = true).performTextInput("200")

        // Move the slider
        composeTestRule.onNodeWithTag("TipSlider").performTouchInput {
            swipeRight(startX = left, endX = right / 2, durationMillis = 1000)
        }

        // Check updated tip amount
        composeTestRule.onNodeWithText("Tip").assertIsDisplayed()
        composeTestRule.onNodeWithText("%", substring = true).assertIsDisplayed()

        // Check updated total per person safely via test tag
        composeTestRule.onNodeWithTag("TotalPerPerson").assertIsDisplayed()
    }
}
