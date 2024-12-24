package com.joohnq.user.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.joohnq.domain.constants.Constants
import com.joohnq.ui.state.UiState
import com.joohnq.user.domain.entity.User
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
class UserViewModelTest {
				@get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
				private lateinit var userViewModel: UserViewModel
				private lateinit var ioDispatcher: CoroutineDispatcher
				private lateinit var userRepository: UserRepositoryImpl
				private lateinit var userObserver: Observer<com.joohnq.ui.state.UiState<User>>
				private lateinit var user: User

				@Before
				fun setUp() {
								user = mockk<User>(relaxed = true)
								userRepository = mockk<UserRepositoryImpl>()
								ioDispatcher = UnconfinedTestDispatcher()
								userViewModel = UserViewModel(
												userRepository,
												ioDispatcher
								)
								userObserver = mockk<Observer<com.joohnq.ui.state.UiState<User>>>(relaxed = true)
								userViewModel.get.observeForever(userObserver)
				}

				@Test
				fun `test fetchUser with valid user return, should return Loading, then Success`() {
								coEvery { userRepository.getUser() } returns user

								userViewModel.get()

								val slots = mutableListOf<com.joohnq.ui.state.UiState<User>>()
								verify { userObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(com.joohnq.ui.state.UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(com.joohnq.ui.state.UiState.Success(user))
				}

				@Test
				fun `test fetchUser with valid user return, should return Loading, then Failure`() {
								coEvery { userRepository.getUser() } throws Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)

								userViewModel.get()

								val slots = mutableListOf<com.joohnq.ui.state.UiState<User>>()
								verify { userObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(com.joohnq.ui.state.UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(com.joohnq.ui.state.UiState.Failure(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR))
				}

				@Test
				fun `test updateUser with true operation, should fetch user and return Loading, then Success`() {
								coEvery { userRepository.insertOrUpdate(any()) } returns true
								coEvery { userRepository.getUser() } returns user

								userViewModel.update(user)

								val slots = mutableListOf<com.joohnq.ui.state.UiState<User>>()
								verify { userObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(com.joohnq.ui.state.UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(com.joohnq.ui.state.UiState.Success(user))
				}

				@Test
				fun `test updateUser with true operation and a failure fetch user, should return Loading, then Failure`() {
								coEvery { userRepository.insertOrUpdate(any()) } returns true
								coEvery { userRepository.getUser() } throws Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)

								userViewModel.update(user)

								val slots = mutableListOf<com.joohnq.ui.state.UiState<User>>()
								verify { userObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(com.joohnq.ui.state.UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(com.joohnq.ui.state.UiState.Failure(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR))
				}

				@Test
				fun `test updateUser with false operation, should Loading, then Failure`() {
								coEvery { userRepository.insertOrUpdate(any()) } returns false

								userViewModel.update(user)

								val slots = mutableListOf<com.joohnq.ui.state.UiState<User>>()
								verify { userObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(com.joohnq.ui.state.UiState.Loading)
								Truth.assertThat(slots[1])
												.isEqualTo(com.joohnq.ui.state.UiState.Failure(com.joohnq.domain.exceptions.FirebaseException.ErrorOnUpdateUser().message))
				}

				@Test
				fun `test updateUserOccupation with valid occupation, should return Loading, then Success`() {
								coEvery { userRepository.updateUserOccupation(any()) } returns true
								coEvery { userRepository.getUser() } returns user.copy(occupation = "")

								userViewModel.updateUserOccupation("")

								val slots = mutableListOf<com.joohnq.ui.state.UiState<User>>()
								verify { userObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(com.joohnq.ui.state.UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(com.joohnq.ui.state.UiState.Success(user.copy(occupation = "")))
				}

				@Test
				fun `test updateUserOccupation with valid occupation, but with an unsuccess fetchUser, should return Loading, then Failure`() {
								coEvery { userRepository.updateUserOccupation(any()) } returns true
								coEvery { userRepository.getUser() } throws Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)

								userViewModel.updateUserOccupation("")

								val slots = mutableListOf<com.joohnq.ui.state.UiState<User>>()
								verify { userObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(com.joohnq.ui.state.UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(com.joohnq.ui.state.UiState.Failure(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR))
				}

				@Test
				fun `test updateUserOccupation with valid occupation, but with an unsuccess updateUserOccupation, should return Loading, then Failure`() {
								coEvery { userRepository.updateUserOccupation(any()) } returns false

								userViewModel.updateUserOccupation("")

								val slots = mutableListOf<com.joohnq.ui.state.UiState<User>>()
								verify { userObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(com.joohnq.ui.state.UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(com.joohnq.ui.state.UiState.Failure(com.joohnq.domain.exceptions.FirebaseException.ErrorOnUpdateUserOccupation().message))
				}

				@Test
				fun `test updateUserImageUrl with success uploadUserImage,uploadUserImageUrl and fetchUser, should return Loading, then Success`() {
								val url = "url.com.br"
								coEvery { userRepository.uploadUserImage(any()) } returns url
								coEvery { userRepository.updateUserImageUrl(any()) } returns true
								coEvery { userRepository.getUser() } returns user.copy(imageUrl = url)

								userViewModel.updateUserImageUrl(mockk())

								val slots = mutableListOf<com.joohnq.ui.state.UiState<User>>()
								verify { userObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(com.joohnq.ui.state.UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(com.joohnq.ui.state.UiState.Success(user.copy(imageUrl = url)))
				}

				@Test
				fun `test updateUserImageUrl with failure uploadUserImage, should return Loading, then Failure`() {
								coEvery { userRepository.uploadUserImage(any()) } throws Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)

								userViewModel.updateUserImageUrl(mockk())

								val slots = mutableListOf<com.joohnq.ui.state.UiState<User>>()
								verify { userObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(com.joohnq.ui.state.UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(com.joohnq.ui.state.UiState.Failure(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR))
				}

				@Test
				fun `test updateUserImageUrl with success uploadUserImage, but with an unsuccess updateUserImageUrl, should return Loading, then Failure`() {
								val url = "url.com.br"
								coEvery { userRepository.uploadUserImage(any()) } returns url
								coEvery { userRepository.updateUserImageUrl(any()) } returns false

								userViewModel.updateUserImageUrl(mockk())

								val slots = mutableListOf<com.joohnq.ui.state.UiState<User>>()
								verify { userObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(com.joohnq.ui.state.UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(com.joohnq.ui.state.UiState.Failure(com.joohnq.domain.exceptions.FirebaseException.ErrorOnUpdateUserImage().message))
				}

				@Test
				fun `test updateUserImageUrl with success uploadUserImage and updateUserImageUrl, but with an unsuccess fetchUser, should return Loading, then Failure`() {
								val url = "url.com.br"
								coEvery { userRepository.uploadUserImage(any()) } returns url
								coEvery { userRepository.updateUserImageUrl(any()) } returns true
								coEvery { userRepository.getUser() } throws Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)

								userViewModel.updateUserImageUrl(mockk())

								val slots = mutableListOf<com.joohnq.ui.state.UiState<User>>()
								verify { userObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(com.joohnq.ui.state.UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(com.joohnq.ui.state.UiState.Failure(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR))
				}
}