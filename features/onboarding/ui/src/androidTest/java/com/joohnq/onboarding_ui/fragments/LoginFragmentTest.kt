package com.joohnq.onboarding_ui.fragments

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.textfield.TextInputLayout
import com.joohnq.core.exceptions.EmailValidatorException
import com.joohnq.onboarding_ui.R
import com.joohnq.onboarding_ui.launchFragmentInHiltContainer
import com.joohnq.ui.viewmodel.AuthViewModel
import com.joohnq.ui.fragments.LoginFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class LoginFragmentTest {
				private lateinit var navController: NavController
				@get:Rule var hiltRule = HiltAndroidRule(this)
				private lateinit var authRepository: AuthRepository
				private lateinit var authViewModel: AuthViewModel

				@Before
				fun setUp() {
								hiltRule.inject()
								navController = mock(NavHostController::class.java)
								InstrumentationRegistry.getInstrumentation().runOnMainSync {
												navController.setGraph(R.navigation.onboarding_graph)
								}
								authRepository = mockk<AuthRepository>(relaxed = true)
								authViewModel = AuthViewModel(
												userRepository = mockk(relaxed = true),
												authRepository = authRepository,
												dispatcher = mockk(relaxed = true),
												googleAuthRepository = mockk(relaxed = true)
								)
								launchFragmentInHiltContainer<LoginFragment> {
												Navigation.setViewNavController(requireView(), navController)
								}
				}

				private fun hasTextInputLayoutErrorText(expectedErrorText: String) =
								object: BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {
												override fun matchesSafely(textInputLayout: TextInputLayout): Boolean {
																return expectedErrorText == textInputLayout.error?.toString()
												}

												override fun describeTo(description: org.hamcrest.Description) {
																description.appendText("with error: $expectedErrorText")
												}
								}

				@Test
				fun testNavigationToLoginFragment() {
								onView(withId(R.id.btnLetsGetStarted)).perform(click())
								onView(withId(R.id.textInputEditTextEmailLogin)).perform(replaceText("joao"))
//								onView(withId(R.id.textInputEditTextPasswordLogin)).perform(replaceText("joao"))
								coEvery { authViewModel.signIn(any(), any()) } returns Unit
								coEvery { authRepository.signInWithEmailAndPassword(any(), any()) } returns true

								onView(withId(R.id.btnLogin)).perform(click())
								onView(withId(R.id.textInputLayoutEmailLogin)).check(
												matches(
																hasTextInputLayoutErrorText(
																				EmailValidatorException.EmailInvalid().message.toString()
																)
												)
								)
//								verify(navController).navigate(R.id.action_onboardingFragment_to_loginFragment)
				}
}
