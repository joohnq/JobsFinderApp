package com.joohnq.core.ui.navigation

import android.content.Context
import com.joohnq.job.domain.entity.Job

interface ShowAllNavigation {
				fun navigateToJobDetailActivity(context: Context, job: Job)
}