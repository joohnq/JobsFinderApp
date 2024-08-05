package com.joohnq.application_ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.application_data.repository.ApplicationRepository
import com.joohnq.core.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicationsViewModel @Inject constructor(
				private val applicationRepository: ApplicationRepository,
				private val ioDispatcher: CoroutineDispatcher,
): ViewModel() {
				private val _applications = MutableLiveData<UiState<List<String>>>()
				val applications: LiveData<UiState<List<String>>>
								get() = _applications

				fun add(id: String) {
								_applications.postValue(UiState.Loading)
								viewModelScope.launch(ioDispatcher) {
												try {
																val res = applicationRepository.add(id)

																if (!res) throw Throwable("Error on add job application")

																fetch()
												} catch (e: Exception) {
																_applications.postValue(UiState.Failure(e.message))
												}
								}
				}

				fun remove(id: String) {
								_applications.postValue(UiState.Loading)
								viewModelScope.launch(ioDispatcher) {
												try {
																val res = applicationRepository.remove(id)

																if (!res) throw Throwable("Error on remove job application")

																fetch()
												} catch (e: Exception) {
																_applications.postValue(UiState.Failure(e.message))
												}
								}
				}

				fun fetch() {
								_applications.postValue(UiState.Loading)
								viewModelScope.launch(ioDispatcher) {
												try {
																val jobs = applicationRepository.getJobApplication()

																_applications.postValue(UiState.Success(jobs))
												} catch (e: Exception) {
																_applications.postValue(UiState.Failure(e.message))
												}
								}
				}
}