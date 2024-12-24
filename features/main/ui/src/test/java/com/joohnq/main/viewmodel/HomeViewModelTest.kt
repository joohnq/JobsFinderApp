package com.joohnq.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.joohnq.domain.constants.Constants
import com.joohnq.job_data.JobsDatabaseRepository
import com.joohnq.job_data.repository.JobsDatabaseRepositoryImpl
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
class HomeViewModelTest {
				@get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
				private lateinit var successList: List<Job>
				private lateinit var ioDispatcher: CoroutineDispatcher
				private lateinit var jobsDatabaseRepository: JobsDatabaseRepository
				private lateinit var homeViewModel: HomeViewModel
				private lateinit var homeObserver: Observer<com.joohnq.ui.state.UiState<List<Job>>>
				private val occupation = "Android"

				@Before
				fun setUp() {
								successList = mockk<List<Job>>(relaxed = true)
								ioDispatcher = UnconfinedTestDispatcher()
								jobsDatabaseRepository = mockk<JobsDatabaseRepositoryImpl>()
								homeViewModel = HomeViewModel(
												repository = jobsDatabaseRepository,
												dispatcher = ioDispatcher,
								)
								homeObserver = mockk<Observer<com.joohnq.ui.state.UiState<List<Job>>>>(relaxed = true)
								homeViewModel.homeJobs.observeForever(homeObserver)
				}

				@Test
				fun `test getHomeJobs with valid occupation and valid jobs return, should return Loading, then Success`() {
								coEvery {
												jobsDatabaseRepository.getJobsBySearch(any(), any())
								} returns successList

								homeViewModel.getHomeJobs(occupation)

								val slots = mutableListOf<com.joohnq.ui.state.UiState<List<Job>>>()
								verify { homeObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(com.joohnq.ui.state.UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(com.joohnq.ui.state.UiState.Success(successList))
				}

				@Test
				fun `test getHomeJobs with null occupation, should return Loading, then Failure`() {
								homeViewModel.getHomeJobs(null)

								val slots = mutableListOf<com.joohnq.ui.state.UiState<List<Job>>>()
								verify { homeObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(com.joohnq.ui.state.UiState.Loading)
								Truth.assertThat(slots[1])
												.isEqualTo(com.joohnq.ui.state.UiState.Failure(com.joohnq.domain.exceptions.FirebaseException.UserIdIsNull().message))
				}

				@Test
				fun `test getHomeJobs with valid occupation and with exception from repository, should return Loading, then Failure`() {
								coEvery {
												jobsDatabaseRepository.getJobsBySearch(any(), any())
								} throws Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)

								homeViewModel.getHomeJobs(occupation)

								val slots = mutableListOf<com.joohnq.ui.state.UiState<List<Job>>>()
								verify { homeObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(com.joohnq.ui.state.UiState.Loading)
								Truth.assertThat(slots[1])
												.isEqualTo(com.joohnq.ui.state.UiState.Failure(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR))
				}
}
