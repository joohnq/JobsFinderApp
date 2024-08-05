package com.joohnq.favorite_ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.core.exceptions.FirebaseException
import com.joohnq.core.mappers.setIfNewValue
import com.joohnq.core.state.UiState
import com.joohnq.favorite_data.repository.FavoriteRepository
import com.joohnq.job_data.repository.JobRepository
import com.joohnq.job_domain.entities.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
				private val favoritesRepository: FavoriteRepository,
				private val ioDispatcher: CoroutineDispatcher,
				private val jobRepository: JobRepository
): ViewModel() {
				private val _favoritesIds = MutableLiveData<UiState<List<String>>>()
				val favorites: LiveData<UiState<List<String>>>
								get() = _favoritesIds

				private val _favoritesJobsDetails = MutableLiveData<UiState<List<Job>>>()
				val favoritesDetails: LiveData<UiState<List<Job>>>
								get() = _favoritesJobsDetails

				fun add(id: String) {
								viewModelScope.launch(ioDispatcher) {
												_favoritesIds.setIfNewValue(UiState.Loading)
												_favoritesJobsDetails.setIfNewValue(UiState.Loading)
												try {
																val res = favoritesRepository.add(id)

																if (!res) throw FirebaseException.ErrorOnAddFavorite()

																fetch()
												} catch (e: Exception) {
																_favoritesIds.postValue(UiState.Failure(e.message))
																_favoritesJobsDetails.postValue(UiState.Failure(e.message))
												}
								}
				}

				fun remove(id: String) {
								viewModelScope.launch(ioDispatcher) {
												_favoritesIds.setIfNewValue(UiState.Loading)
												try {
																val res = favoritesRepository.remove(id)

																if (!res) throw FirebaseException.ErrorOnRemoveFavorite()

																fetch()
												} catch (e: Exception) {
																_favoritesIds.postValue(UiState.Failure(e.message))
												}
								}
				}

				fun fetch() {
								viewModelScope.launch(ioDispatcher) {
												_favoritesIds.setIfNewValue(UiState.Loading)
												try {
																_favoritesIds.postValue(UiState.Success(favoritesRepository.fetch()))
												} catch (e: Exception) {
																_favoritesIds.postValue(UiState.Failure(e.message))
												}
								}
				}

				fun fetchFavoritesDetails(ids: List<String>) {
								viewModelScope.launch(ioDispatcher) {
												_favoritesJobsDetails.setIfNewValue(UiState.Loading)
												try {
																jobRepository.fetchJobDetail(ids)
																				.catch { throw it }
																				.collect {
																								_favoritesJobsDetails.postValue(UiState.Success(it))
																				}
												} catch (e: Exception) {
																_favoritesJobsDetails.postValue(UiState.Failure(e.message))
												}
								}
				}
}