package com.joohnq.onboarding_ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.joohnq.core.exceptions.FirebaseException
import com.joohnq.core.state.UiState
import com.joohnq.ui.viewmodel.AuthViewModel
import com.joohnq.data.repository.UserRepository
import com.joohnq.domain.entity.User
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
class AuthViewModelTest {
				@get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
				private lateinit var googleAuthRepository: com.joohnq.data.repository.GoogleAuthRepository
				private lateinit var userRepository: UserRepository
				private lateinit var authRepository: AuthRepository
				private lateinit var authViewModel: AuthViewModel
				private var ioDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
				private val email: String = "joao@gmail.com"
				private val password: String = "password"
				private lateinit var authObserver: Observer<UiState<String>>
				private lateinit var stateObserver: Observer<UiState<String>>
				private val user: User = User(
								email = "joao@gmail.com",
								name = "joao"
				)

				@Before
				fun setUp() {
								authRepository = mockk<AuthRepository>()
								googleAuthRepository = mockk<com.joohnq.data.repository.GoogleAuthRepository>()
								userRepository = mockk<UserRepositoryImpl>()

								authViewModel = AuthViewModel(
												authRepository = authRepository,
												googleAuthRepository = googleAuthRepository,
												dispatcher = ioDispatcher,
												userRepository = userRepository
								)

								authObserver = mockk<Observer<UiState<String>>>(relaxed = true)
								stateObserver = mockk<Observer<UiState<String>>>(relaxed = true)
								authViewModel.auth.observeForever(authObserver)
								authViewModel.resetEmail.observeForever(stateObserver)
				}

				@Test
				fun `when sendPasswordResetEmail should verify if email exists then change state to Loading to Success`() {
								coEvery {
												userRepository.verifyIfEmailExists(any())
								} returns true
								coEvery {
												authRepository.sendPasswordResetEmail(any())
								} returns true

								authViewModel.sendPasswordResetEmail(email)

								val slots = mutableListOf<UiState<String>>()
								verify { authObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Success(""))
								coVerify { userRepository.verifyIfEmailExists(any()) }
								coVerify { authRepository.sendPasswordResetEmail(any()) }
				}

				@Test
				fun `when sendPasswordResetEmail should return false and change auth to Failure`() {
								val errorMessage = FirebaseException.ErrorOnLogin().message
								coEvery {
												authRepository.signInWithEmailAndPassword(any(), any())
								} returns false

								authViewModel.signIn(email, password)

								val slots = mutableListOf<UiState<String>>()
								verify { authObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Failure(errorMessage))
								coVerify { authRepository.signInWithEmailAndPassword(any(), any()) }
				}

				@Test
				fun `when signInWithEmailAndPassword should return true and change auth to Success`() {
								coEvery {
												authRepository.signInWithEmailAndPassword(any(), any())
								} returns true

								authViewModel.signIn(email, password)

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

								authViewModel.signIn(email, password)

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
												userRepository.insertOrUpdate(any())
								} returns true

								coEvery {
												authRepository.signInWithEmailAndPassword(any(), any())
								} returns true

								authViewModel.signUp(user, password)

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

								authViewModel.signUp(user, password)

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
												userRepository.insertOrUpdate(user)
								} returns false

								authViewModel.signUp(user, password)

								val slots = mutableListOf<UiState<String>>()
								verify { authObserver.onChanged(capture(slots)) }

								Truth.assertThat(slots[0]).isEqualTo(UiState.Loading)
								Truth.assertThat(slots[1]).isEqualTo(UiState.Failure(message))

								coVerify { authRepository.createUserWithEmailAndPassword(any(), any()) }
				}
}
