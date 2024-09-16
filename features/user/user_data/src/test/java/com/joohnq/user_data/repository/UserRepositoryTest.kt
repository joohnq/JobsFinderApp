package com.joohnq.user_data.repository

import android.net.Uri
import com.google.android.gms.tasks.OnCompleteListener
import com.google.common.truth.Truth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.UploadTask.TaskSnapshot
import com.joohnq.core.constants.Constants
import com.joohnq.core.constants.FirebaseConstants
import com.joohnq.core.exceptions.FirebaseException
import com.joohnq.core_test.mockk.mockTask
import com.joohnq.user_domain.entities.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserRepositoryTest {
				private lateinit var userRepository: UserRepository
				private lateinit var auth: FirebaseAuth
				private lateinit var db: FirebaseFirestore
				private lateinit var storage: FirebaseStorage
				private val userId: String = "2"

				@Before
				fun setUp() {
								storage = mockk(relaxed = true)
				}

				@Test
				fun `test userUid with a valid user, should return true`() = runTest {
								auth = mockk(relaxed = true) { every { currentUser?.uid } returns userId }
								db = mockk(relaxed = true)
								userRepository = UserRepository(auth, db, storage)

								val res = userRepository.userUid()
								Truth.assertThat(res).isNotEmpty()
								Truth.assertThat(res).isEqualTo(userId)
				}

				@Test(expected = Exception::class)
				fun `test userUid with a invalid user value, should return an exception`() = runTest {
								auth = mockk(relaxed = true) { every { currentUser?.uid } throws FirebaseException.UserIdIsNull() }
								db = mockk(relaxed = true)
								userRepository = UserRepository(auth, db, storage)

								val res = userRepository.userUid()
								Truth.assertThat(res).isEmpty()
								Truth.assertThat(res).isNotEqualTo(userId)
				}

				@Test
				fun `test updateUser with a valid user url, should return true`() = runTest {
								val userMockk = mockk<User>(relaxed = true)
								auth = mockk(relaxed = true) { every { currentUser?.uid } returns userId }
								db = mockk(relaxed = true)
								userRepository = UserRepository(auth, db, storage)

								val task = mockTask<Void>(null)

								every { task.addOnCompleteListener(any()) } answers {
												firstArg<OnCompleteListener<Void>>().onComplete(task)
												task
								}

								every {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any())
																.set(any<User>())
								} returns task

								val res = userRepository.updateUser(userMockk)

								verify(exactly = 1) {
												db.collection(FirebaseConstants.FIREBASE_USER).document(any<String>()).set(any<User>())
								}

								Truth.assertThat(res).isNotNull()
								Truth.assertThat(res).isTrue()
				}

				@Test(expected = Exception::class)
				fun `test updateUser with a valid user url, but with a error updating, should return an exception`() =
								runTest {
												val exception = Exception(Constants.TEST_SOME_ERROR)
												val userMockk = mockk<User>(relaxed = true)
												auth = mockk(relaxed = true) { every { currentUser?.uid } returns userId }
												db = mockk(relaxed = true)
												userRepository = UserRepository(auth, db, storage)

												val task = mockTask<Void>(null, exception)

												every { task.addOnCompleteListener(any()) } answers {
																firstArg<OnCompleteListener<Void>>().onComplete(task)
																task
												}

												every {
																db.collection(FirebaseConstants.FIREBASE_USER)
																				.document(any())
																				.set(any<User>())
												} returns task

												val res = userRepository.updateUser(userMockk)

												verify(exactly = 1) {
																db.collection(FirebaseConstants.FIREBASE_USER)
																				.document(any<String>())
																				.set(any<User>())
												}

												Truth.assertThat(res).isNotNull()
												Truth.assertThat(res).isTrue()
								}

				@Test
				fun `test updateUserImageUrl with a valid user url, should return true`() = runTest {
								auth = mockk(relaxed = true) { every { currentUser?.uid } returns userId }
								db = mockk(relaxed = true)
								userRepository = UserRepository(auth, db, storage)

								val task = mockTask<Void>(null)

								every { task.addOnCompleteListener(any()) } answers {
												firstArg<OnCompleteListener<Void>>().onComplete(task)
												task
								}

								every {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any())
																.update(any<Map<String, String>>())
								} returns task

								val res = userRepository.updateUserImageUrl("url")

								verify(exactly = 1) {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any<String>())
																.update(any<Map<String, String>>())
								}

								Truth.assertThat(res).isNotNull()
								Truth.assertThat(res).isTrue()
				}

				@Test(expected = Exception::class)
				fun `test updateUserImageUrl with a valid user url, but with a error updating, should return an exception`() =
								runTest {
												val exception = Exception(Constants.TEST_SOME_ERROR)
												auth = mockk(relaxed = true) { every { currentUser?.uid } returns userId }
												db = mockk(relaxed = true)
												userRepository = UserRepository(auth, db, storage)

												val task = mockTask<Void>(null, exception)

												every { task.addOnCompleteListener(any()) } answers {
																firstArg<OnCompleteListener<Void>>().onComplete(task)
																task
												}

												every {
																db.collection(FirebaseConstants.FIREBASE_USER)
																				.document(any())
																				.update(any<Map<String, String>>())
												} returns task

												userRepository.updateUserImageUrl("url")

												verify(exactly = 1) {
																db.collection(FirebaseConstants.FIREBASE_USER)
																				.document(any<String>())
																				.update(any<Map<String, String>>())
												}
								}

				@Test
				fun `test fetchUser with a valid user url, should return true`() = runTest {
								val userMockk = mockk<User>(relaxed = true)
								auth = mockk(relaxed = true) { every { currentUser?.uid } returns userId }
								db = mockk(relaxed = true)
								userRepository = UserRepository(auth, db, storage)

								val documentSnapshot = mockk<DocumentSnapshot>(relaxed = true) {
												every { toObject(User::class.java) } returns userMockk
								}
								val task = mockTask<DocumentSnapshot>(documentSnapshot)

								every { task.addOnCompleteListener(any()) } answers {
												firstArg<OnCompleteListener<DocumentSnapshot>>().onComplete(task)
												task
								}

								every { db.collection(FirebaseConstants.FIREBASE_USER).document(any()).get() } returns task

								val user = userRepository.fetchUser()

								verify(exactly = 1) {
												db.collection(FirebaseConstants.FIREBASE_USER).document(any<String>()).get()
								}

								Truth.assertThat(user).isNotNull()
								Truth.assertThat(user).isEqualTo(userMockk)
				}

				@Test(expected = Exception::class)
				fun `test fetchUser with a valid user url, but with a error updating, should return an exception`() =
								runTest {
												val exception = Exception(Constants.TEST_SOME_ERROR)
												auth = mockk(relaxed = true) { every { currentUser?.uid } returns userId }
												db = mockk(relaxed = true)
												userRepository = UserRepository(auth, db, storage)

												val documentSnapshot = mockk<DocumentSnapshot>(relaxed = true)
												val task = mockTask<DocumentSnapshot>(documentSnapshot, exception)

												every { task.addOnCompleteListener(any()) } answers {
																firstArg<OnCompleteListener<DocumentSnapshot>>().onComplete(task)
																task
												}

												every {
																db.collection(FirebaseConstants.FIREBASE_USER)
																				.document(any())
																				.get()
												} returns task

												userRepository.fetchUser()

												verify(exactly = 1) {
																db.collection(FirebaseConstants.FIREBASE_USER).document(any<String>()).get()
												}
								}

				@Test
				fun `test uploadUserImage with a valid user uri, should return true`() = runTest {
								val uri = mockk<Uri>(relaxed = true)
								auth = mockk(relaxed = true) { every { currentUser?.uid } returns userId }
								db = mockk(relaxed = true)
								userRepository = UserRepository(auth, db, storage)

								val uploadTask = mockk<UploadTask>(relaxed = true)

								every { uploadTask.addOnCompleteListener(any()) } answers {
												every { uploadTask.isSuccessful } returns true
												firstArg<OnCompleteListener<TaskSnapshot>>().onComplete(uploadTask)
												uploadTask
								}

								every {
												storage.getReference(FirebaseConstants.FIREBASE_USERS)
																.child(FirebaseConstants.FIREBASE_PHOTOS)
																.child(any<String>())
																.putFile(uri)
								} returns uploadTask

								val res = userRepository.uploadUserImage(uri)

								verify(exactly = 1) {
												storage.getReference(FirebaseConstants.FIREBASE_USERS)
																.child(FirebaseConstants.FIREBASE_PHOTOS)
																.child(any<String>())
																.putFile(uri)
								}

								Truth.assertThat(res).isTrue()
				}

				@Test(expected = Exception::class)
				fun `test uploadUserImage with a valid user uri, but with a error updating, should return an exception`() =
								runTest {
												val exception = Exception(Constants.TEST_SOME_ERROR)
												val uri = mockk<Uri>(relaxed = true)
												auth = mockk(relaxed = true) { every { currentUser?.uid } returns userId }
												db = mockk(relaxed = true)
												userRepository = UserRepository(auth, db, storage)

												val uploadTask = mockk<UploadTask>(relaxed = true)

												every { uploadTask.addOnCompleteListener(any()) } answers {
																every { uploadTask.isSuccessful } returns false
																every { uploadTask.exception } returns exception
																firstArg<OnCompleteListener<TaskSnapshot>>().onComplete(uploadTask)
																uploadTask
												}

												every {
																storage.getReference(FirebaseConstants.FIREBASE_USERS)
																				.child(FirebaseConstants.FIREBASE_PHOTOS)
																				.child(any<String>())
																				.putFile(uri)
												} returns uploadTask

												userRepository.uploadUserImage(uri)

												verify(exactly = 1) {
																storage.getReference(FirebaseConstants.FIREBASE_USERS)
																				.child(FirebaseConstants.FIREBASE_PHOTOS)
																				.child(any<String>())
																				.putFile(uri)
												}
								}

				@Test
				fun `test updateUserOccupation with a valid user occupation, should return true`() = runTest {
								auth = mockk(relaxed = true) { every { currentUser?.uid } returns userId }
								db = mockk(relaxed = true)
								userRepository = UserRepository(auth, db, storage)

								val task = mockTask<Void>(null)

								every { task.addOnCompleteListener(any()) } answers {
												firstArg<OnCompleteListener<Void>>().onComplete(task)
												task
								}

								every {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any())
																.update(any<Map<String, String>>())
								} returns task

								val res = userRepository.updateUserOccupation("occupation")

								verify(exactly = 1) {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any<String>())
																.update(any<Map<String, String>>())
								}

								Truth.assertThat(res).isNotNull()
								Truth.assertThat(res).isTrue()
				}

				@Test(expected = Exception::class)
				fun `test updateUserOccupation with a valid user occupation, but with a error updating, should return an exception`() =
								runTest {
												val exception = Exception(Constants.TEST_SOME_ERROR)
												auth = mockk(relaxed = true) { every { currentUser?.uid } returns userId }
												db = mockk(relaxed = true)
												userRepository = UserRepository(auth, db, storage)

												val task = mockTask<Void>(null, exception)

												every { task.addOnCompleteListener(any()) } answers {
																firstArg<OnCompleteListener<Void>>().onComplete(task)
																task
												}

												every {
																db.collection(FirebaseConstants.FIREBASE_USER)
																				.document(any())
																				.update(any<Map<String, String>>())
												} returns task

												userRepository.updateUserOccupation("occupation")

												verify(exactly = 1) {
																db.collection(FirebaseConstants.FIREBASE_USER)
																				.document(any<String>())
																				.update(any<Map<String, String>>())
												}
								}

				@Test
				fun `test signOut, should return true`() = runTest {
								auth = mockk(relaxed = true) { every { currentUser?.uid } returns userId }
								db = mockk(relaxed = true)
								userRepository = UserRepository(auth, db, storage)

								val res = userRepository.signOut()

								verify(exactly = 1) {
												auth.signOut()
								}

								Truth.assertThat(res).isTrue()
				}

				@Test(expected = Exception::class)
				fun `test signOut, but with a error, should return an exception`() =
								runTest {
												auth = mockk(relaxed = true) {
																every { currentUser?.uid } returns userId
																every { signOut() } throws Exception(Constants.TEST_SOME_ERROR)
												}
												db = mockk(relaxed = true)
												userRepository = UserRepository(auth, db, storage)

												val res = userRepository.signOut()

												verify(exactly = 1) {
																auth.signOut()
												}

												Truth.assertThat(res).isTrue()
								}
}