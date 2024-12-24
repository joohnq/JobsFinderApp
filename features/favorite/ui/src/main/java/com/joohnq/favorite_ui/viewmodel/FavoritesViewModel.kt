package com.joohnq.favorite_ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.mappers.setIfNewValue
import com.joohnq.ui.state.UiState
import com.joohnq.favorite_data.repository.FavoritesRepository
import com.joohnq.data.repository.JobRepository
import com.joohnq.domain.entity.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
				private val favoritesRepository: FavoritesRepository,
				private val ioDispatcher: CoroutineDispatcher,
				private val jobRepository: JobRepository
): ViewModel() {
				private val _favoritesIds = MutableLiveData<MutableList<String>>()
				val favoritesIds: LiveData<MutableList<String>>
								get() = _favoritesIds

				private val _favoritesJobsDetails = MutableLiveData<com.joohnq.ui.state.UiState<MutableList<Job>>>()
				val favoritesDetails: LiveData<com.joohnq.ui.state.UiState<MutableList<Job>>>
								get() = _favoritesJobsDetails

				fun toggle(id: String) = viewModelScope.launch(ioDispatcher) {
								val currentFavorites = favoritesIds.value ?: mutableListOf()
								if (currentFavorites.contains(id)) {
												val res = favoritesRepository.remove(id)
												if (res) currentFavorites.remove(id)
								} else {
												val res = favoritesRepository.add(id)
												if (res) currentFavorites.add(id)
								}
								_favoritesIds.postValue(currentFavorites)
				}

				fun fetch() {
								viewModelScope.launch(ioDispatcher) {
												try {
																val res = favoritesRepository.fetch()
																_favoritesIds.postValue(res.toMutableList())
																fetchFavoritesDetails(res)
												} catch (e: Exception) {
																_favoritesIds.postValue(mutableListOf())
												}
								}
				}

				fun fetchFavoritesDetails(ids: List<String>) {
								viewModelScope.launch(ioDispatcher) {
												_favoritesJobsDetails.setIfNewValue(com.joohnq.ui.state.UiState.Loading)
												try {
																val jobs = jobRepository.getJobsByIds(ids)
																_favoritesJobsDetails.postValue(com.joohnq.ui.state.UiState.Success(jobs.toMutableList()))
												} catch (e: Exception) {
																_favoritesJobsDetails.postValue(com.joohnq.ui.state.UiState.Failure(e.message))
												}
								}
				}

				fun setFavoritesIdsForTesting(ids: MutableList<String>) {
								_favoritesIds.postValue(ids)
				}
}