package com.joohhq.ui.state

import com.joohnq.domain.entity.Job
import com.joohnq.domain.entity.UiState

data class HomeViewModelState(
				val jobs: UiState<List<Job>> = UiState.None
)