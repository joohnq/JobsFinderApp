package com.joohnq.job_data.repository

import com.google.common.truth.Truth
import com.joohnq.core.constants.Constants
import com.joohnq.job_data.JobsDatabaseRepository
import com.joohnq.job_domain.entities.Job
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class JobsDatabaseRepositoryImplTest {
				private lateinit var jobsDatabaseRepository: JobsDatabaseRepository
				private lateinit var successList: List<Job>

				@Before
				fun setUp() {
								successList = mockk<List<Job>>(relaxed = true)
								jobsDatabaseRepository = mockk<JobsDatabaseRepositoryImpl>(relaxed = true)
				}

				@Test
				fun `test getRemoteJobs with successfully return, should is not empty and equal to mocked list`() =
								runTest {
												coEvery { jobsDatabaseRepository.getRemoteJobs() } returns successList
												val jobs = jobsDatabaseRepository.getRemoteJobs()
												Truth.assertThat(jobs).isNotEmpty()
												Truth.assertThat(jobs).isEqualTo(successList)
												coVerify { jobsDatabaseRepository.getRemoteJobs() }
								}

				@Test(expected = Exception::class)
				fun `test getRemoteJobs with failure return, should return exception`() = runTest {
								coEvery { jobsDatabaseRepository.getRemoteJobs() } throws Exception(Constants.TEST_SOME_ERROR)
								jobsDatabaseRepository.getRemoteJobs()
								coVerify { jobsDatabaseRepository.getRemoteJobs() }
				}

				@Test
				fun `test getPartTimeJobs with successfully return, should is not empty and equal to mocked list`() =
								runTest {
												coEvery { jobsDatabaseRepository.getPartTimeJobs() } returns successList
												val jobs = jobsDatabaseRepository.getPartTimeJobs()
												Truth.assertThat(jobs).isNotEmpty()
												Truth.assertThat(jobs).isEqualTo(successList)
												coVerify { jobsDatabaseRepository.getPartTimeJobs() }
								}

				@Test(expected = Exception::class)
				fun `test getPartTimeJobs with failure return, should return exception`() = runTest {
								coEvery { jobsDatabaseRepository.getPartTimeJobs() } throws Exception(Constants.TEST_SOME_ERROR)
								jobsDatabaseRepository.getPartTimeJobs()
								coVerify { jobsDatabaseRepository.getPartTimeJobs() }
				}

				@Test
				fun `test getFullTimeJobs with successfully return, should is not empty and equal to mocked list`() =
								runTest {
												coEvery { jobsDatabaseRepository.getFullTimeJobs() } returns successList
												val jobs = jobsDatabaseRepository.getFullTimeJobs()
												Truth.assertThat(jobs).isNotEmpty()
												Truth.assertThat(jobs).isEqualTo(successList)
												coVerify { jobsDatabaseRepository.getFullTimeJobs() }
								}

				@Test(expected = Exception::class)
				fun `test getFullTimeJobs with failure return, should return exception`() = runTest {
								coEvery { jobsDatabaseRepository.getFullTimeJobs() } throws Exception(Constants.TEST_SOME_ERROR)
								jobsDatabaseRepository.getFullTimeJobs()
								coVerify { jobsDatabaseRepository.getFullTimeJobs() }
				}

				@Test
				fun `test getJobsBySearch with successfully return, should is not empty and equal to mocked list`() =
								runTest {
												coEvery {
																jobsDatabaseRepository.getJobsBySearch(
																				any<String>(),
																				any<Long>()
																)
												} returns successList
												val jobs = jobsDatabaseRepository.getJobsBySearch("test", 20)
												Truth.assertThat(jobs).isNotEmpty()
												Truth.assertThat(jobs).isEqualTo(successList)
												coVerify {
																jobsDatabaseRepository.getJobsBySearch(
																				any<String>(),
																				any<Long>()
																)
												}
								}

				@Test(expected = Exception::class)
				fun `test getJobsBySearch with failure return, should return exception`() = runTest {
								coEvery {
												jobsDatabaseRepository.getJobsBySearch(
																any<String>(),
																any<Long>()
												)
								} throws Exception(Constants.TEST_SOME_ERROR)
								jobsDatabaseRepository.getJobsBySearch("test", 20)
								coVerify {
												jobsDatabaseRepository.getJobsBySearch(
																any<String>(),
																any<Long>()
												)
								}
				}

				@Test
				fun `test getJobsBySearch offset_limit with successfully return, should is not empty and equal to mocked list`() =
								runTest {
												coEvery {
																jobsDatabaseRepository.getJobsBySearch(
																				any<String>(),
																				any<Long>(),
																				any<Long>()
																)
												} returns successList
												val jobs = jobsDatabaseRepository.getJobsBySearch("test", 20, 40)
												Truth.assertThat(jobs).isNotEmpty()
												Truth.assertThat(jobs).isEqualTo(successList)
												coVerify {
																jobsDatabaseRepository.getJobsBySearch(
																				any<String>(),
																				any<Long>(),
																				any<Long>()
																)
												}
								}

				@Test(expected = Exception::class)
				fun `test getJobsBySearch offset_limit with failure return, should return exception`() =
								runTest {
												coEvery {
																jobsDatabaseRepository.getJobsBySearch(
																				any<String>(),
																				any<Long>(),
																				any<Long>(),
																)
												} throws Exception(Constants.TEST_SOME_ERROR)
												jobsDatabaseRepository.getJobsBySearch("test", 20, 40)
												coVerify {
																jobsDatabaseRepository.getJobsBySearch(
																				any<String>(),
																				any<Long>(),
																				any<Long>()
																)
												}
								}

				@Test
				fun `test getJobById with successfully return, should be equal to mocked job`() =
								runTest {
												val successJob = mockk<Job>(relaxed = true)
												coEvery {
																jobsDatabaseRepository.getJobById(any<String>())
												} returns successJob
												val job = jobsDatabaseRepository.getJobById("test")
												Truth.assertThat(job).isEqualTo(successJob)
												coVerify {
																jobsDatabaseRepository.getJobById(any<String>())
												}
								}

				@Test(expected = Exception::class)
				fun `test getJobById with failure return, should return exception`() =
								runTest {
												coEvery {
																jobsDatabaseRepository.getJobById(any<String>())
												} throws Exception(Constants.TEST_SOME_ERROR)
												jobsDatabaseRepository.getJobById("test")
												coVerify {
																jobsDatabaseRepository.getJobById(any<String>())
												}
								}

				@Test
				fun `test getJobByIds with successfully return, should is not empty and equal to mocked list`() =
								runTest {
												coEvery {
																jobsDatabaseRepository.getJobsByIds(any<List<String>>())
												} returns successList
												val jobs = jobsDatabaseRepository.getJobsByIds(mockk())
												Truth.assertThat(jobs).isNotEmpty()
												Truth.assertThat(jobs).isEqualTo(successList)
												coVerify {
																jobsDatabaseRepository.getJobsByIds(any<List<String>>())
												}
								}

				@Test(expected = Exception::class)
				fun `test getJobByIds with failure return, should return exception`() =
								runTest {
												coEvery {
																jobsDatabaseRepository.getJobsByIds(any<List<String>>())
												} throws Exception(Constants.TEST_SOME_ERROR)
												jobsDatabaseRepository.getJobsByIds(mockk())
												coVerify {
																jobsDatabaseRepository.getJobsByIds(any<List<String>>())
												}
								}
}