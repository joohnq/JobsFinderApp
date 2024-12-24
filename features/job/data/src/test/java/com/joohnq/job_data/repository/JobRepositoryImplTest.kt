package com.joohnq.job_data.repository

import com.google.common.truth.Truth
import com.joohnq.data.repository.JobRepositoryImpl
import com.joohnq.domain.constants.Constants
import com.joohnq.data.repository.JobRepository
import com.joohnq.domain.entity.Job
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class JobRepositoryImplTest {
				private lateinit var jobRepository: JobRepository
				private lateinit var successList: List<Job>

				@Before
				fun setUp() {
								successList = mockk<List<Job>>(relaxed = true)
								jobRepository = mockk<JobRepositoryImpl>(relaxed = true)
				}

				@Test
				fun `test getRemoteJobs with successfully return, should is not empty and equal to mocked list`() =
								runTest {
												coEvery { jobRepository.getRemoteJobs() } returns successList
												val jobs = jobRepository.getRemoteJobs()
												Truth.assertThat(jobs).isNotEmpty()
												Truth.assertThat(jobs).isEqualTo(successList)
												coVerify { jobRepository.getRemoteJobs() }
								}

				@Test(expected = Exception::class)
				fun `test getRemoteJobs with failure return, should return exception`() = runTest {
								coEvery { jobRepository.getRemoteJobs() } throws Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)
								jobRepository.getRemoteJobs()
								coVerify { jobRepository.getRemoteJobs() }
				}

				@Test
				fun `test getPartTimeJobs with successfully return, should is not empty and equal to mocked list`() =
								runTest {
												coEvery { jobRepository.getPartTimeJobs() } returns successList
												val jobs = jobRepository.getPartTimeJobs()
												Truth.assertThat(jobs).isNotEmpty()
												Truth.assertThat(jobs).isEqualTo(successList)
												coVerify { jobRepository.getPartTimeJobs() }
								}

				@Test(expected = Exception::class)
				fun `test getPartTimeJobs with failure return, should return exception`() = runTest {
								coEvery { jobRepository.getPartTimeJobs() } throws Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)
								jobRepository.getPartTimeJobs()
								coVerify { jobRepository.getPartTimeJobs() }
				}

				@Test
				fun `test getFullTimeJobs with successfully return, should is not empty and equal to mocked list`() =
								runTest {
												coEvery { jobRepository.getFullTimeJobs() } returns successList
												val jobs = jobRepository.getFullTimeJobs()
												Truth.assertThat(jobs).isNotEmpty()
												Truth.assertThat(jobs).isEqualTo(successList)
												coVerify { jobRepository.getFullTimeJobs() }
								}

				@Test(expected = Exception::class)
				fun `test getFullTimeJobs with failure return, should return exception`() = runTest {
								coEvery { jobRepository.getFullTimeJobs() } throws Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)
								jobRepository.getFullTimeJobs()
								coVerify { jobRepository.getFullTimeJobs() }
				}

				@Test
				fun `test getJobsBySearch with successfully return, should is not empty and equal to mocked list`() =
								runTest {
												coEvery {
																jobRepository.getJobsBySearch(
																				any<String>(),
																				any<Long>()
																)
												} returns successList
												val jobs = jobRepository.getJobsBySearch("test", 20)
												Truth.assertThat(jobs).isNotEmpty()
												Truth.assertThat(jobs).isEqualTo(successList)
												coVerify {
																jobRepository.getJobsBySearch(
																				any<String>(),
																				any<Long>()
																)
												}
								}

				@Test(expected = Exception::class)
				fun `test getJobsBySearch with failure return, should return exception`() = runTest {
								coEvery {
												jobRepository.getJobsBySearch(
																any<String>(),
																any<Long>()
												)
								} throws Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)
								jobRepository.getJobsBySearch("test", 20)
								coVerify {
												jobRepository.getJobsBySearch(
																any<String>(),
																any<Long>()
												)
								}
				}

				@Test
				fun `test getJobsBySearch offset_limit with successfully return, should is not empty and equal to mocked list`() =
								runTest {
												coEvery {
																jobRepository.getJobsBySearch(
																				any<String>(),
																				any<Long>(),
																				any<Long>()
																)
												} returns successList
												val jobs = jobRepository.getJobsBySearch("test", 20, 40)
												Truth.assertThat(jobs).isNotEmpty()
												Truth.assertThat(jobs).isEqualTo(successList)
												coVerify {
																jobRepository.getJobsBySearch(
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
																jobRepository.getJobsBySearch(
																				any<String>(),
																				any<Long>(),
																				any<Long>(),
																)
												} throws Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)
												jobRepository.getJobsBySearch("test", 20, 40)
												coVerify {
																jobRepository.getJobsBySearch(
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
																jobRepository.getJobById(any<String>())
												} returns successJob
												val job = jobRepository.getJobById("test")
												Truth.assertThat(job).isEqualTo(successJob)
												coVerify {
																jobRepository.getJobById(any<String>())
												}
								}

				@Test(expected = Exception::class)
				fun `test getJobById with failure return, should return exception`() =
								runTest {
												coEvery {
																jobRepository.getJobById(any<String>())
												} throws Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)
												jobRepository.getJobById("test")
												coVerify {
																jobRepository.getJobById(any<String>())
												}
								}

				@Test
				fun `test getJobByIds with successfully return, should is not empty and equal to mocked list`() =
								runTest {
												coEvery {
																jobRepository.getJobsByIds(any<List<String>>())
												} returns successList
												val jobs = jobRepository.getJobsByIds(mockk())
												Truth.assertThat(jobs).isNotEmpty()
												Truth.assertThat(jobs).isEqualTo(successList)
												coVerify {
																jobRepository.getJobsByIds(any<List<String>>())
												}
								}

				@Test(expected = Exception::class)
				fun `test getJobByIds with failure return, should return exception`() =
								runTest {
												coEvery {
																jobRepository.getJobsByIds(any<List<String>>())
												} throws Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)
												jobRepository.getJobsByIds(mockk())
												coVerify {
																jobRepository.getJobsByIds(any<List<String>>())
												}
								}
}