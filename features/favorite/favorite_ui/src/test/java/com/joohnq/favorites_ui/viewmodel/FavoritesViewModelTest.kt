package com.joohnq.favorites_ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.joohnq.core.constants.Constants
import com.joohnq.core.state.UiState
import com.joohnq.favorite_data.repository.FavoritesRepository
import com.joohnq.favorite_ui.viewmodel.FavoritesViewModel
import com.joohnq.job_data.JobsDatabaseRepository
import com.joohnq.job_data.repository.JobsDatabaseRepositoryImpl
import com.joohnq.job_domain.entities.Job
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {
				@get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
				private var ioDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
				private lateinit var favoritesRepository: FavoritesRepository
				private lateinit var jobsDatabaseRepository: JobsDatabaseRepository
				private lateinit var favoritesViewModel: FavoritesViewModel
				private lateinit var favoritesObserver: Observer<MutableList<String>>
				private lateinit var favoritesDetailsObserver: Observer<UiState<MutableList<Job>>>
				private lateinit var successIdsList: List<String>
				private lateinit var successJobsList: List<Job>
				private val id: String = "1"

				@Before
				fun setUp() {
								successJobsList = mockk<List<Job>>(relaxed = true)
								successIdsList = mockk<List<String>>(relaxed = true)
								favoritesRepository = mockk<FavoritesRepository>()
								jobsDatabaseRepository = mockk<JobsDatabaseRepositoryImpl>()
								favoritesViewModel = FavoritesViewModel(
												favoritesRepository = favoritesRepository,
												jobsDatabaseRepository = jobsDatabaseRepository,
												ioDispatcher = ioDispatcher,
								)
								favoritesObserver = mockk<Observer<MutableList<String>>>(relaxed = true)
								favoritesDetailsObserver = mockk<Observer<UiState<MutableList<Job>>>>(relaxed = true)
								favoritesViewModel.favoritesIds.observeForever(favoritesObserver)
								favoritesViewModel.favoritesDetails.observeForever(favoritesDetailsObserver)
				}

				@Test
				fun `test fetch favorites with valid favorites return, should return the successList`() {
								coEvery { favoritesRepository.fetch() } returns successIdsList

								favoritesViewModel.fetch()

								val slots = mutableListOf<MutableList<String>>()
								verify { favoritesObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(successIdsList)
				}

				@Test
				fun `test fetch favorites with invalid return, should return an empty list`() {
								coEvery { favoritesRepository.fetch() } throws Exception(Constants.TEST_SOME_ERROR)

								favoritesViewModel.fetch()

								val slots = mutableListOf<MutableList<String>>()
								verify { favoritesObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(mutableListOf<String>())
				}

				@Test
				fun `test toggle favorites with a non-existent value in list and with a success repository return, should add on list`() {
								coEvery { favoritesRepository.add(any()) } returns true

								favoritesViewModel.toggle(id)

								val slots = mutableListOf<MutableList<String>>()
								verify { favoritesObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(mutableListOf(id))
				}

				@Test
				fun `test toggle favorites with a non-existent value in list and with a error repository return, should do nothing`() {
								coEvery { favoritesRepository.add(any()) } returns false

								favoritesViewModel.toggle(id)

								val slots = mutableListOf<MutableList<String>>()
								verify { favoritesObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(mutableListOf<String>())
				}

				@Test
				fun `test toggle favorites with a existent value in list and with a success repository return, should remove from list`() {
								coEvery { favoritesRepository.remove(any()) } returns true

								favoritesViewModel.setFavoritesIdsForTesting(mutableListOf(id))

								favoritesViewModel.toggle(id)

								val slots = mutableListOf<MutableList<String>>()
								verify { favoritesObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(mutableListOf<String>())
				}

				@Test
				fun `test toggle favorites with a existent value in list and with a error repository return, should do nothing`() {
								coEvery { favoritesRepository.add(any()) } returns false

								favoritesViewModel.setFavoritesIdsForTesting(mutableListOf(id))

								favoritesViewModel.toggle(id)

								val slots = mutableListOf<MutableList<String>>()
								verify { favoritesObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(mutableListOf(id))
				}

				@Test
				fun `test fetchFavoritesDetails with a valid list of ids and with a success repository return, should return Loading, then Success`() {
								coEvery { jobsDatabaseRepository.getJobsByIds(any()) } returns successJobsList

								favoritesViewModel.fetchFavoritesDetails(mutableListOf(id))

								val slots = mutableListOf<UiState<MutableList<Job>>>()
								verify { favoritesDetailsObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Success(successJobsList))
				}

				@Test
				fun `test fetchFavoritesDetails with a valid list of ids and with a error repository return, should return Loading, then Failure`() {
								coEvery { jobsDatabaseRepository.getJobsByIds(any()) } throws Exception(Constants.TEST_SOME_ERROR)

								favoritesViewModel.fetchFavoritesDetails(mutableListOf(id))

								val slots = mutableListOf<UiState<MutableList<Job>>>()
								verify { favoritesDetailsObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Failure(Constants.TEST_SOME_ERROR))
				}
}