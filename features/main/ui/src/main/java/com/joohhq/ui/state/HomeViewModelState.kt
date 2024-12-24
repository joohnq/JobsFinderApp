package com.joohhq.ui.state

import com.joohnq.domain.entity.Job
import com.joohnq.ui.state.UiState

data class HomeViewModelState(
				val jobs: UiState<List<Job>> = UiState.None
)