package com.joohnq.core_test.mockk.firebase

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.joohnq.domain.constants.Constants
import com.joohnq.domain.constants.FirebaseConstants
import com.joohnq.core_test.mockk.mockTask
import com.joohnq.user.domain.entity.User
import io.mockk.MockKAnswerScope
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class FirebaseUserSetMockkHelper(private val taskCompletionSource: TaskCompletionSource<Void>):
				FirebaseUserMockkHelper() {
				fun createDocumentReferenceSetUser(): DocumentReference = mockk {
								every {
												set(any<User>())
								} returns mockTask<Void>(null)
				}

				fun createDocumentReferenceSetUserException(): DocumentReference = mockk {
								every { set(any<User>()) } returns mockTask(
												null,
												Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)
								)
				}

				fun everyDatabaseSetUser(db: FirebaseFirestore, task: Task<Void>): Task<Void> = mockk(relaxed = true) {
								every {
												db.collection(com.joohnq.domain.constants.FirebaseConstants.FIREBASE_USER)
																.document(any<String>())
																.set(any<User>())
								} returns task
				}

				fun coEveryAnswersSetUser(
								mockk: MockKAnswerScope<Boolean, Boolean>,
								auth: FirebaseAuth,
								db: FirebaseFirestore,
				): Boolean {
								val user = mockk.firstArg<User>()
								val userId = auth.currentUser?.uid ?: throw Exception(com.joohnq.domain.constants.Constants.TEST_SOME_ERROR)

								val task = db
												.collection(com.joohnq.domain.constants.FirebaseConstants.FIREBASE_USER)
												.document(userId)
												.set(user)

								return task.isSuccessful
				}

				fun verifyDatabaseSetUser(db: FirebaseFirestore) = verify(exactly = 1) {
								db
												.collection(com.joohnq.domain.constants.FirebaseConstants.FIREBASE_USER)
												.document(any<String>())
												.set(any<User>())
				}
}