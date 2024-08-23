package com.joohnq.onboarding_ui.fragments

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.joohnq.onboarding_ui.R
import com.joohnq.onboarding_ui.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class OnboardingFragmentTest {
				private lateinit var navController: NavController
				@get:Rule var hiltRule = HiltAndroidRule(this)

				@Before
				fun setUp() {
								hiltRule.inject()
								navController = mock(NavHostController::class.java)
								InstrumentationRegistry.getInstrumentation().runOnMainSync {
												navController.setGraph(R.navigation.onboarding_graph)
								}
								launchFragmentInHiltContainer<OnboardingFragment> {
												Navigation.setViewNavController(requireView(), navController)
								}
				}

				@Test
				fun testNavigationToLoginFragment() {
								onView(withId(R.id.btnLetsGetStarted)).perform(click())
								verify(navController).navigate(R.id.action_onboardingFragment_to_loginFragment)
				}
}
