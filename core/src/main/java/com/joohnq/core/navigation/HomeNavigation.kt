package com.joohnq.core.navigation

import android.content.Context
import com.joohnq.show_all_domain.entities.ShowAllType

interface HomeNavigation {
				fun navigateToJobDetailActivity(context: Context, id: String)

				fun navigateToSearchActivity(context: Context)

				fun navigateToProfileActivity(context: Context): Unit

				fun navigateToShowAllActivity(
								context: Context,
								showAllType: ShowAllType
				)
}