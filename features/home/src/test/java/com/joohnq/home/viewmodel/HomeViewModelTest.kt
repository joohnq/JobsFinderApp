package com.joohnq.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.joohnq.core.constants.Constants
import com.joohnq.core.exceptions.FirebaseException
import com.joohnq.core.state.UiState
import com.joohnq.job_data.JobsDatabaseRepository
import com.joohnq.job_data.repository.JobsDatabaseRepositoryImpl
import com.joohnq.job_domain.entities.Job
import io.mockk.coEvery
import io.mockk.coVerify
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
				private lateinit var homeObserver: Observer<UiState<List<Job>>>
				private val occupation = "Android"

				@Before
				fun setUp() {
								successList = mockk<List<Job>>(relaxed = true)
								ioDispatcher = UnconfinedTestDispatcher()
								jobsDatabaseRepository = mockk<JobsDatabaseRepositoryImpl>()
								homeViewModel = HomeViewModel(
												jobsDatabaseRepository = jobsDatabaseRepository,
												ioDispatcher = ioDispatcher,
								)
								homeObserver = mockk<Observer<UiState<List<Job>>>>(relaxed = true)
								homeViewModel.homeJobs.observeForever(homeObserver)
				}

				@Test
				fun `test getHomeJobs with valid occupation and valid jobs return, should return Loading, then Success`() {
								coEvery {
												jobsDatabaseRepository.getJobsBySearch(any(), any())
								} returns successList

								homeViewModel.getHomeJobs(occupation)

								val slots = mutableListOf<UiState<List<Job>>>()
								verify { homeObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Success(successList))
				}

				@Test
				fun `test getHomeJobs with null occupation, should return Loading, then Failure`() {
								homeViewModel.getHomeJobs(null)

								val slots = mutableListOf<UiState<List<Job>>>()
								verify { homeObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1])
												.isEqualTo(UiState.Failure(FirebaseException.UserIdIsNull().message))
				}

				@Test
				fun `test getHomeJobs with valid occupation and with exception from repository, should return Loading, then Failure`() {
								coEvery {
												jobsDatabaseRepository.getJobsBySearch(any(), any())
								} throws Exception(Constants.TEST_SOME_ERROR)

								homeViewModel.getHomeJobs(occupation)

								val slots = mutableListOf<UiState<List<Job>>>()
								verify { homeObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1])
												.isEqualTo(UiState.Failure(Constants.TEST_SOME_ERROR))
				}
}
