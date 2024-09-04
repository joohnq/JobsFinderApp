package com.joohnq.favorite_ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.core.mappers.setIfNewValue
import com.joohnq.core.state.UiState
import com.joohnq.favorite_data.repository.FavoriteRepository
import com.joohnq.job_data.JobsDatabaseRepository
import com.joohnq.job_domain.entities.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
				private val favoritesRepository: FavoriteRepository,
				private val ioDispatcher: CoroutineDispatcher,
				private val jobsDatabaseRepository: JobsDatabaseRepository
): ViewModel() {
				private val _favoritesIds = MutableLiveData<MutableList<String>>()
				val favoritesIds: LiveData<MutableList<String>>
								get() = _favoritesIds

				private val _favoritesJobsDetails = MutableLiveData<UiState<MutableList<Job>>>()
				val favoritesDetails: LiveData<UiState<MutableList<Job>>>
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
												_favoritesJobsDetails.setIfNewValue(UiState.Loading)
												try {
																val jobs = jobsDatabaseRepository.getJobsByIds(ids)
																_favoritesJobsDetails.postValue(UiState.Success(jobs.toMutableList()))
												} catch (e: Exception) {
																_favoritesJobsDetails.postValue(UiState.Failure(e.message))
												}
								}
				}
}