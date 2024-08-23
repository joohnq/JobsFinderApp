package com.joohnq.onboarding_data.repository

import com.google.common.truth.Truth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.joohnq.user_domain.entities.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AuthRepositoryTest {
				private lateinit var authRepository: AuthRepository
				private val email: String = "joao@gmail.com"
				private val password: String = "password"
				private val user: User = User(email = "joao@gmail.com", name = "joao")

				@Before
				fun setUp() {
								authRepository = mockk<AuthRepository>(relaxed = true)
				}

				private suspend fun createUserWithEmailAndPassword(): User {
								return authRepository.createUserWithEmailAndPassword(user, password)
				}

				private suspend fun signInWithEmailAndPassword(): Boolean {
								return authRepository.signInWithEmailAndPassword(email, password)
				}

				@Test
				fun `createUserWithEmailAndPassword should return user with updated id`() {
								coEvery { createUserWithEmailAndPassword() } returns user.copy(id = "123")
								runBlocking {
												val res = createUserWithEmailAndPassword()
												Truth.assertThat(res).isNotNull()
												Truth.assertThat(res.id).isEqualTo("123")
								}
				}

				@Test(expected = FirebaseAuthEmailException::class)
				fun `when call createUserWithEmailAndPassword with an invalid email should return a FirebaseAuthEmailException`() {
								coEvery {
												createUserWithEmailAndPassword()
								} throws mockk<FirebaseAuthEmailException>(relaxed = true)
								runBlocking {
												createUserWithEmailAndPassword()
								}
				}

				@Test(expected = FirebaseAuthWeakPasswordException::class)
				fun `when call createUserWithEmailAndPassword with an invalid password should return a FirebaseAuthWeakPasswordException`() {
								coEvery {
												createUserWithEmailAndPassword()
								} throws mockk<FirebaseAuthWeakPasswordException>(relaxed = true)
								runBlocking {
												createUserWithEmailAndPassword()
								}
				}

				@Test
				fun `when call signInWithEmailAndPassword with valid fields should return true`() {
								coEvery {
												signInWithEmailAndPassword()
								} returns true
								runBlocking {
												val res = signInWithEmailAndPassword()
												Truth.assertThat(res).isNotNull()
												Truth.assertThat(res).isEqualTo(true)
								}
				}

				@Test(expected = FirebaseAuthEmailException::class)
				fun `when call signInWithEmailAndPassword with an invalid email should return a FirebaseAuthEmailException`() {
								coEvery {
												signInWithEmailAndPassword()
								} throws mockk<FirebaseAuthEmailException>(relaxed = true)
								runBlocking {
												signInWithEmailAndPassword()
								}
				}

				@Test(expected = FirebaseAuthInvalidCredentialsException::class)
				fun `when call signInWithEmailAndPassword with an invalid credentials should return a FirebaseAuthInvalidCredentialsException`() {
								coEvery {
												signInWithEmailAndPassword()
								} throws mockk<FirebaseAuthInvalidCredentialsException>(relaxed = true)
								runBlocking {
												signInWithEmailAndPassword()
								}
				}
}
