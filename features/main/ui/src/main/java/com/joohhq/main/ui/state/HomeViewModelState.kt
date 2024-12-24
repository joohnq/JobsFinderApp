package com.joohhq.main.ui.state

import com.joohnq.job.domain.entity.Job
import com.joohnq.core.domain.entity.UiState

data class HomeViewModelState(
				val jobs: UiState<List<Job>> = UiState.None
)