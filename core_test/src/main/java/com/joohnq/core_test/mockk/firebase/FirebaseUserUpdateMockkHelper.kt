package com.joohnq.core_test.mockk.firebase

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.joohnq.core.constants.Constants
import com.joohnq.core.constants.FirebaseConstants
import com.joohnq.core_test.mockk.mockTask
import io.mockk.MockKAnswerScope
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class FirebaseUserUpdateMockkHelper(private val taskCompletionSource: TaskCompletionSource<Void>):
				FirebaseUserMockkHelper() {
				fun createDocumentReferenceUpdateUser(): DocumentReference = mockk {
								every {
												update(
																FirebaseConstants.FIREBASE_FAVORITES,
																any<FieldValue>()
												)
								} returns mockTask<Void>(null)
				}

				fun createDocumentReferenceUpdateUserImageUrl(): DocumentReference = mockk {
								every {
												update(any<Map<String, String>>())
								} returns mockTask<Void>(null)
				}

				fun createDocumentReferenceUpdateUserImageUrlException(): DocumentReference = mockk {
								every {
												update(any<Map<String, String>>())
								} returns mockTask(
												null,
												Exception(Constants.TEST_SOME_ERROR)
								)
				}

				fun createDocumentReferenceUpdateUserException(): DocumentReference = mockk {
								every { update(FirebaseConstants.FIREBASE_FAVORITES, any<FieldValue>()) } returns mockTask(
												null,
												Exception(Constants.TEST_SOME_ERROR)
								)
				}

				fun everyDatabaseUpdateUserImageUrl(db: FirebaseFirestore, task: Task<Void>): Task<Void> =
								mockk {
												every {
																db.collection(FirebaseConstants.FIREBASE_USER)
																				.document(any())
																				.update(any<Map<String, String>>())
												} returns task
								}

				fun everyDatabaseUpdateUserImageUrlException(db: FirebaseFirestore) = every {
								db.collection(FirebaseConstants.FIREBASE_USER)
												.document(any())
												.update(any<Map<String, String>>())
				} returns mockTask(null, Exception(Constants.TEST_SOME_ERROR))

				fun everyDatabaseUpdateUserFavorites(db: FirebaseFirestore) = every {
								db.collection(FirebaseConstants.FIREBASE_USER)
												.document(any())
												.update(FirebaseConstants.FIREBASE_FAVORITES, any<FieldValue>())
				} returns taskCompletionSource.task

				fun everyDatabaseUpdateUserFavoritesException(db: FirebaseFirestore) =
								every {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any())
																.update(FirebaseConstants.FIREBASE_FAVORITES, any<FieldValue>())
								} returns mockTask(null, Exception(Constants.TEST_SOME_ERROR))

				fun coEveryAnswersUpdateUserFavorites(
								mockk: MockKAnswerScope<Boolean, Boolean>,
								auth: FirebaseAuth,
								db: FirebaseFirestore,
								status: Boolean
				): Boolean {
								val itemId = mockk.firstArg<String>()
								val userId = auth.currentUser?.uid ?: return@coEveryAnswersUpdateUserFavorites false

								val task = db
												.collection(FirebaseConstants.FIREBASE_USER)
												.document(userId)
												.update(
																FirebaseConstants.FIREBASE_FAVORITES,
																if (status) FieldValue.arrayUnion(itemId) else FieldValue.arrayRemove(itemId)
												)

								return task.isSuccessful
				}

				fun coEveryAnswersUpdateUserImageUrl(
								mockk: MockKAnswerScope<Boolean, Boolean>,
								auth: FirebaseAuth,
								db: FirebaseFirestore,
				): Boolean {
								val url = mockk.firstArg<String>()
								val userId = auth.currentUser?.uid ?: return@coEveryAnswersUpdateUserImageUrl false

								val task = db
												.collection(FirebaseConstants.FIREBASE_USER)
												.document(userId)
												.update(mapOf(FirebaseConstants.FIREBASE_IMAGE_URL to url))

								return task.isSuccessful
				}

				fun verifyDatabaseUpdateUserImageUrl(db: FirebaseFirestore) = verify(exactly = 1) {
								db
												.collection(FirebaseConstants.FIREBASE_USER)
												.document(any<String>())
												.update(any<Map<String, String>>())
				}

				fun verifyDatabaseUpdateUserFavorites(db: FirebaseFirestore) = verify(exactly = 1) {
								db
												.collection(FirebaseConstants.FIREBASE_USER)
												.document(any<String>())
												.update(
																FirebaseConstants.FIREBASE_FAVORITES,
																any<FieldValue>()
												)
				}
}