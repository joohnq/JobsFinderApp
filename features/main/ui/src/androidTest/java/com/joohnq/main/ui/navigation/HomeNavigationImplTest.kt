package com.joohnq.main.ui.navigation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.joohnq.main.fragments.HomeFragment
import com.joohnq.main.ui.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.joohnq.main.R as HomeR

@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class HomeNavigationImplTest {
				@get:Rule var hiltRule = HiltAndroidRule(this)
				@get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

				@Before
				fun setUp() {
								hiltRule.inject()
								launchFragmentInHiltContainer<HomeFragment> {
								}
				}

				@Test
				fun testNavigateToFragmentB() {
								onView(withId(HomeR.id.rvHomeJobs)).check { view, _ ->
												println(view)
								}
				}
}