package com.example.core2

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isSelected
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun navigateProduct(){
        onView(withId(R.id.nextButton)).perform(click())
        onView(withId(R.id.productName)).check(matches(withText(PRODUCT_NAME_2)))
        onView(withId(R.id.productPrice)).check(matches(withText("$6")))

        onView(withId(R.id.nextButton)).perform(click())
        onView(withId(R.id.productName)).check(matches(withText(PRODUCT_NAME_3)))
    }

    @Test
    fun loopProduct() {
        val clickCount = 4
        for (i in 1..clickCount) {
            onView(withId(R.id.nextButton)).perform(click())
        }
        onView(withId(R.id.productName)).check(matches(withText(PRODUCT_NAME_1)))
    }

    @Test
    fun rentButton(){
        onView(withId(R.id.rentButton)).perform(click())
        onView(withId(R.id.rentLength)).check(matches(withText("Rent Length (per day)")))
        onView(withId(R.id.productName)).check(matches(withText(PRODUCT_NAME_1)))
    }

    @Test
    fun multipleChoiceForGame1(){
        onView(withId(R.id.PGbutton)).check(matches(isSelected()))
        onView(withId(R.id.Mbutton)).check(matches(isSelected()))
        onView(withId(R.id.button3)).check(matches(isSelected()))
    }

    @Test
    fun snackBarBack(){
        onView(withId(R.id.rentButton)).perform(click())
        onView(withId(R.id.cancelButton)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(withText("Bye!")))
    }

    @Test
    fun snackBarSaveWith0Days(){
        onView(withId(R.id.rentButton)).perform(click())
        onView(withId(R.id.saveButton)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(withText("You must select at least 1 day!")))
    }

    companion object {
        val PRODUCT_NAME_1 = "Kirby and the Forgotten Land"
        val PRODUCT_NAME_2 = "Legends of Zelda Tears of Kingdom"
        val PRODUCT_NAME_3 = "MarioKart 8 Deluxe"
    }
}