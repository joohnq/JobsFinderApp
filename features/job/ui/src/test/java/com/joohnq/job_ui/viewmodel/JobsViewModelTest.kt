package com.joohnq.job_ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.joohnq.domain.constants.Constants
import com.joohnq.domain.entity.UiState
import com.joohnq.data.repository.JobRepository
import com.joohnq.domain.entity.Job
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
class JobsViewModelTest {
				@get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
				private lateinit var successList: List<Job>
				private lateinit var jobsViewModel: JobsViewModel
				private lateinit var ioDispatcher: CoroutineDispatcher
				private lateinit var jobRepository: JobRepository
				private lateinit var remoteJobsObserver: Observer<UiState<List<Job>>>
				private lateinit var partTimeJobsObserver: Observer<UiState<List<Job>>>
				private lateinit var fullTimeJobsObserver: Observer<UiState<List<Job>>>

				@Before
				fun setUp() {
								successList = mockk<List<Job>>(relaxed = true)
								jobRepository = mockk<JobRepositoryImpl>()
								ioDispatcher = UnconfinedTestDispatcher()
								jobsViewModel = JobsViewModel(
												jobRepository,
												ioDispatcher
								)
								remoteJobsObserver = mockk<Observer<UiState<List<Job>>>>(relaxed = true)
								partTimeJobsObserver = mockk<Observer<UiState<List<Job>>>>(relaxed = true)
								fullTimeJobsObserver = mockk<Observer<UiState<List<Job>>>>(relaxed = true)
								jobsViewModel.remoteJobs.observeForever(remoteJobsObserver)
								jobsViewModel.partTimeJobs.observeForever(partTimeJobsObserver)
								jobsViewModel.fullTimeJobs.observeForever(fullTimeJobsObserver)
				}

				@Test
				fun `test getRemoteJobs with valid jobs return, should return Loading, then Success`() {
								coEvery { jobRepository.getRemoteJobs() } returns successList

								jobsViewModel.getRemoteJobs()

								val slots = mutableListOf<UiState<List<Job>>>()
								verify { remoteJobsObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Success(successList))
				}

				@Test
				fun `test getRemoteJobs with invalid jobs return, should return Loading, then Failure`() {
								coEvery { jobRepository.getRemoteJobs() } throws Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)

								jobsViewModel.getRemoteJobs()

								val slots = mutableListOf<UiState<List<Job>>>()
								verify { remoteJobsObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Failure(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR))
				}

				@Test
				fun `test getPartTimeJobs with valid jobs return, should return Loading, then Success`() {
								coEvery { jobRepository.getPartTimeJobs() } returns successList

								jobsViewModel.getPartTimeJobs()

								val slots = mutableListOf<UiState<List<Job>>>()
								verify { partTimeJobsObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Success(successList))
				}

				@Test
				fun `test getPartTimeJobs with invalid jobs return, should return Loading, then Failure`() {
								coEvery { jobRepository.getPartTimeJobs() } throws Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)

								jobsViewModel.getPartTimeJobs()

								val slots = mutableListOf<UiState<List<Job>>>()
								verify { partTimeJobsObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Failure(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR))
				}

				@Test
				fun `test getFullTimeJobs with valid jobs return, should return Loading, then Success`() {
								coEvery { jobRepository.getFullTimeJobs() } returns successList

								jobsViewModel.getFullTimeJobs()

								val slots = mutableListOf<UiState<List<Job>>>()
								verify { fullTimeJobsObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Success(successList))
				}

				@Test
				fun `test getFullTimeJobs with invalid jobs return, should return Loading, then Failure`() {
								coEvery { jobRepository.getFullTimeJobs() } throws Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)

								jobsViewModel.getFullTimeJobs()

								val slots = mutableListOf<UiState<List<Job>>>()
								verify { fullTimeJobsObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Failure(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR))
				}
}