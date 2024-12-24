package com.joohnq.job_ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.joohnq.domain.constants.Constants
import com.joohnq.data.repository.JobRepository
import com.joohnq.domain.entity.Job
import com.joohnq.domain.entity.UiState
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
class SearchViewModelTest {
				@get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
				private lateinit var successList: MutableList<Job>
				private lateinit var searchViewModel: SearchViewModel
				private lateinit var ioDispatcher: CoroutineDispatcher
				private lateinit var jobRepository: JobRepository
				private lateinit var searchObserver: Observer<UiState<MutableList<Job>>>

				@Before
				fun setUp() {
								successList = mockk<MutableList<Job>>(relaxed = true)
								jobRepository = mockk<JobRepositoryImpl>()
								ioDispatcher = UnconfinedTestDispatcher()
								searchViewModel = SearchViewModel(
												jobRepository,
												ioDispatcher
								)
								searchObserver = mockk<Observer<UiState<MutableList<Job>>>>(relaxed = true)
								searchViewModel.jobsSearch.observeForever(searchObserver)
				}

				@Test
				fun `test getJobsBySearch with valid jobs return, should return Loading, then Success`() {
								coEvery { jobRepository.getJobsBySearch(any(), any()) } returns successList

								searchViewModel.search("", 20)

								val slots = mutableListOf<UiState<MutableList<Job>>>()
								verify { searchObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.None)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[2]).isEqualTo(UiState.Success(successList))
				}

				@Test
				fun `test getJobsBySearch with exception return, should return Loading, then Failure`() {
								coEvery {
												jobRepository.getJobsBySearch(
																any(),
																any()
												)
								} throws Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)

								searchViewModel.search("", 20)

								val slots = mutableListOf<UiState<MutableList<Job>>>()
								verify { searchObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.None)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[2]).isEqualTo(UiState.Failure(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR))
				}

				@Test
				fun `test searchJobsReload with searchJob initial value empty, should return None, then Success`() {
								coEvery { jobRepository.getJobsBySearch(any(), any(), any()) } returns successList

								searchViewModel.searchJobsReload("", 20, 20)

								val slots = mutableListOf<UiState<MutableList<Job>>>()
								verify { searchObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.None)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Success(successList))
				}

				@Test
				fun `test searchJobsReload with searchJob initial value filled, should return None,Success then Success with new jobs`() {
								val newJobs = mutableListOf<Job>(mockk(), mockk())
								val initialList = mutableListOf<Job>(mockk(), mockk(), mockk(), mockk())
								coEvery { jobRepository.getJobsBySearch(any(), any(), any()) } returns newJobs

								searchViewModel.setJobsSearchForTesting(initialList)

								searchViewModel.searchJobsReload("", 20, 20)

								val slots = mutableListOf<UiState<MutableList<Job>>>()
								verify { searchObserver.onChanged(capture(slots)) }

								val newList: MutableList<Job> = mutableListOf<Job>().apply {
												addAll(initialList)
												addAll(newJobs)
								}

								Truth.assertThat(slots[0]).isEqualTo(UiState.None)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Success(initialList))
								Truth.assertThat(slots[2]).isEqualTo(UiState.Success(newList))
				}

				@Test
				fun `test searchJobsReload with searchJob initial value empty and with getJobsBySearch return empty, should return None,Success then Success with new jobs`() {
								coEvery { jobRepository.getJobsBySearch(any(), any(), any()) } returns emptyList()

								searchViewModel.searchJobsReload("", 20, 20)

								val slots = mutableListOf<UiState<MutableList<Job>>>()
								verify { searchObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.None)
				}

				@Test
				fun `test searchJobsReload with exception return, should return Loading, then Failure`() {
								coEvery { jobRepository.getJobsBySearch(any(), any(), any()) } throws Exception(
												com.joohnq.domain.constants.Constants.TEST_SOME_ERROR
								)

								searchViewModel.searchJobsReload("", 20, 20)

								val slots = mutableListOf<UiState<MutableList<Job>>>()
								verify { searchObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.None)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Failure(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR))
				}
}