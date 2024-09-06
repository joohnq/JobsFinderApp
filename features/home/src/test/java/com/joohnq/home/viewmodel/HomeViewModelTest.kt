package com.joohnq.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.common.truth.Truth
import com.joohnq.core.state.UiState
import com.joohnq.job_data.JobsDatabaseRepository
import com.joohnq.job_data.repository.JobsDatabaseRepositoryImpl
import com.joohnq.job_domain.entities.Job
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
				@get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
				private val successList = listOf(
								Job(
												id = "ocurreret",
												company = "persecuti",
												description = "ipsum",
												location = "vel",
												rating = null,
												salary = null,
												url = "https://www.google.com/#q=ornare",
												positionName = "Linwood Whitaker",
												reviewsCount = null,
												postedAt = null,
												postingDateParsed = null,
												descriptionHTML = "semper",
												externalApplyLink = null,
												searchInput = null,
												isExpired = false,
												companyInfo = null,
												jobType = null
								),
								Job(
												id = "tacimates",
												company = "commune",
												description = "mnesarchum",
												location = "dicunt",
												rating = null,
												salary = null,
												url = "https://duckduckgo.com/?q=invenire",
												positionName = "Doreen Kinney",
												reviewsCount = null,
												postedAt = null,
												postingDateParsed = null,
												descriptionHTML = "possit",
												externalApplyLink = null,
												searchInput = null,
												isExpired = false,
												companyInfo = null,
												jobType = null
								)
				)

				private var ioDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
				private lateinit var jobsDatabaseRepository: JobsDatabaseRepository
				private lateinit var homeViewModel: HomeViewModel
				private lateinit var homeObserver: Observer<UiState<List<Job>>>

				@Before
				fun setUp() {
								jobsDatabaseRepository = mockk<JobsDatabaseRepositoryImpl>()
								homeViewModel = HomeViewModel(
												jobsDatabaseRepository = jobsDatabaseRepository,
												ioDispatcher = ioDispatcher,
								)
								homeObserver = mockk<Observer<UiState<List<Job>>>>(relaxed = true)
								homeViewModel.homeJobs.observeForever(homeObserver)
				}

				@Test
				fun `test getHomeJobs with valid jobs return homeJobs should be Success`() {
								coEvery {
												jobsDatabaseRepository.getJobsBySearch(any(), any())
								} returns successList

								homeViewModel.getHomeJobs("Android")

								val slots = mutableListOf<UiState<List<Job>>>()
								verify { homeObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Success(successList))
								coVerify { jobsDatabaseRepository.getJobsBySearch(any(), any()) }
				}

				@Test
				fun `test getHomeJobs with invalid jobs return homeJobs should be Failure`() {
								val errorMessage = "Error"
								coEvery {
												jobsDatabaseRepository.getJobsBySearch(any(), any())
								} throws Exception(errorMessage)

								homeViewModel.getHomeJobs("Android")

								val slots = mutableListOf<UiState<List<Job>>>()
								verify { homeObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Failure(errorMessage))
								coVerify { jobsDatabaseRepository.getJobsBySearch(any(), any()) }
				}
}
