package com.joohnq.core.navigation

import android.content.Context
import com.joohnq.job_domain.entities.Job

interface ShowAllNavigation {
				fun navigateToJobDetailActivity(context: Context, job: Job)
}