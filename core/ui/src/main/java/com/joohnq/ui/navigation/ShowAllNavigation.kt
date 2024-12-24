package com.joohnq.ui.navigation

import android.content.Context
import com.joohnq.domain.entity.Job

interface ShowAllNavigation {
				fun navigateToJobDetailActivity(context: Context, job: Job)
}