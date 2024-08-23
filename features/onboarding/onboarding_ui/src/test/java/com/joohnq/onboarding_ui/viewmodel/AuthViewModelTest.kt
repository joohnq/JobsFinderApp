package com.joohnq.onboarding_ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.joohnq.core.exceptions.FirebaseException
import com.joohnq.core.state.UiState
import com.joohnq.onboarding_data.repository.AuthRepository
import com.joohnq.onboarding_data.repository.GoogleAuthRepository
import com.joohnq.user.user_ui.viewmodel.UserViewModel
import com.joohnq.user_data.repository.UserRepository
import com.joohnq.user_domain.entities.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {
				@get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
				private lateinit var userViewModel: UserViewModel
				private lateinit var googleAuthRepository: GoogleAuthRepository
				private lateinit var userRepository: UserRepository
				private lateinit var authRepository: AuthRepository
				private lateinit var authViewModel: AuthViewModel
				private var ioDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
				private val email: String = "joao@gmail.com"
				private val password: String = "password"
				private lateinit var authObserver: Observer<UiState<String>>
				private val user: User = User(
								email = "joao@gmail.com",
								name = "joao"
				)

				@Before
				fun setUp() {
								authRepository = mockk<AuthRepository>()
								userViewModel = mockk<UserViewModel>()
								googleAuthRepository = mockk<GoogleAuthRepository>()
								userRepository = mockk<UserRepository>()

								authViewModel = AuthViewModel(
												authRepository = authRepository,
												userViewModel = userViewModel,
												googleAuthRepository = googleAuthRepository,
												ioDispatcher = ioDispatcher,
												userRepository = userRepository
								)

								authObserver = mockk<Observer<UiState<String>>>(relaxed = true)
								authViewModel.auth.observeForever(authObserver)
				}

				@Test
				fun `when signInWithEmailAndPassword should return true and change auth to Success`() {
								coEvery {
												authRepository.signInWithEmailAndPassword(any(), any())
								} returns true

								authViewModel.signInWithEmailAndPassword(email, password)

								val slots = mutableListOf<UiState<String>>()
								verify { authObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Success(""))
								coVerify { authRepository.signInWithEmailAndPassword(any(), any()) }
				}

				@Test
				fun `when signInWithEmailAndPassword should return false and change auth to Failure`() {
								val errorMessage = FirebaseException.ErrorOnLogin().message
								coEvery {
												authRepository.signInWithEmailAndPassword(any(), any())
								} returns false

								authViewModel.signInWithEmailAndPassword(email, password)

								val slots = mutableListOf<UiState<String>>()
								verify { authObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Failure(errorMessage))
								coVerify { authRepository.signInWithEmailAndPassword(any(), any()) }
				}

				@Test
				fun `when createUserWithEmailAndPassword should return user, add user on database and change auth to Success`() {
								coEvery {
												authRepository.createUserWithEmailAndPassword(any(), any())
								} returns user.copy(id = "123")

								coEvery {
												userRepository.updateUser(any())
								} returns true

								coEvery {
												authRepository.signInWithEmailAndPassword(any(), any())
								} returns true

								authViewModel.createUserWithEmailAndPassword(user, password)

								val slots = mutableListOf<UiState<String>>()
								verify { authObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Success(""))

								coVerify { authRepository.createUserWithEmailAndPassword(any(), any()) }
				}

				@Test
				fun `when createUserWithEmailAndPassword should return FirebaseAuthInvalidUserException and change auth to Failure`() {
								coEvery {
												authRepository.createUserWithEmailAndPassword(any(), any())
								} throws mockk<FirebaseAuthInvalidUserException>(relaxed = true)

								authViewModel.createUserWithEmailAndPassword(user, password)

								val slots = mutableListOf<UiState<String>>()
								verify { authObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Failure(""))
								
								coVerify { authRepository.createUserWithEmailAndPassword(any(), any()) }
				}

				@Test
				fun `when createUserWithEmailAndPassword should return user, when insert user o database return false and change auth to Failure`() {
								val message = FirebaseException.ErrorOnCreateUserInDatabase().message
								coEvery {
												authRepository.createUserWithEmailAndPassword(any(), any())
								} returns user

								coEvery {
												userRepository.updateUser(user)
								} returns false

								authViewModel.createUserWithEmailAndPassword(user, password)

								val slots = mutableListOf<UiState<String>>()
								verify { authObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Failure(message))

								coVerify { authRepository.createUserWithEmailAndPassword(any(), any()) }
				}
}
