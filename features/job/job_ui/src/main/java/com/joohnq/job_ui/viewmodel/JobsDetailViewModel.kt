package com.joohnq.job_ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.core.state.UiState
import com.joohnq.job_data.repository.JobRepository
import com.joohnq.job_domain.entities.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsDetailViewModel @Inject constructor(
				private val jobRepository: JobRepository,
				private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
				private val _jobDetail = MutableLiveData<UiState<Job>>()
				val jobDetail: LiveData<UiState<Job>> get() = _jobDetail

				fun getJobDetail(id: String) {
								viewModelScope.launch(ioDispatcher) {
												jobRepository
																.fetchJobDetail(id)
																.catch { _jobDetail.postValue(UiState.Failure(it.message)) }
																.collect { _jobDetail.postValue(UiState.Success(it)) }
								}
				}
}
