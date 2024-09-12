package com.joohnq.home

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import dagger.internal.Preconditions

inline fun <reified T: Fragment> launchFragmentInHiltContainer(
				fragmentArgs: Bundle? = null,
				@StyleRes themeResId: Int = com.joohnq.shared_resources.R.style.JobsFinderAppTheme,
				fragmentFactory: FragmentFactory? = null,
				crossinline action: T.() -> Unit = {}
) {
				val mainActivityIntent = Intent.makeMainActivity(
								ComponentName(
												ApplicationProvider.getApplicationContext(),
												HiltTestActivity::class.java
								)
				).putExtra(
								"FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
								themeResId
				)

				ActivityScenario.launch<HiltTestActivity>(mainActivityIntent).onActivity { activity ->
								fragmentFactory?.let {
												activity.supportFragmentManager.fragmentFactory = it
								}
								val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
												Preconditions.checkNotNull(T::class.java.classLoader),
												T::class.java.name
								)
								fragment.arguments = fragmentArgs

								activity.supportFragmentManager.beginTransaction()
												.add(android.R.id.content, fragment, "")
												.commitNow()

								(fragment as T).action()
				}

}