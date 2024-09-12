package com.joohnq.core_test.mockk.firebase

import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.joohnq.core.constants.Constants
import com.joohnq.core.constants.FirebaseConstants
import com.joohnq.core.exceptions.FirebaseException
import com.joohnq.core_test.mockk.mockTask
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.tasks.await

class FirebaseUserGetMockkHelper(private val taskCompletionSource: TaskCompletionSource<DocumentSnapshot>) {
				fun createDocumentReferenceGetUser(): DocumentReference = mockk {
								every { get() } returns mockTask<DocumentSnapshot>(null)
				}

				fun createDocumentReferenceGetUserException(): DocumentReference = mockk {
								every { get() } returns mockTask(
												null,
												Exception(Constants.TEST_SOME_ERROR)
								)
				}

				fun createCollectionReference(documentReference: DocumentReference): CollectionReference =
								mockk {
												every { document(any()) } returns documentReference
								}

				fun createFirebaseFirestoreUser(collectionReference: CollectionReference): FirebaseFirestore =
								mockk {
												every { collection(FirebaseConstants.FIREBASE_USER) } returns collectionReference
								}

				fun everyDatabaseGetUserFavorites(db: FirebaseFirestore): DocumentSnapshot = mockk {
								every {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any())
																.get()
								} returns taskCompletionSource.task
				}

				fun everyDatabaseGetUserFavoritesException(db: FirebaseFirestore): DocumentSnapshot = mockk {
								every {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any())
																.get()
								} returns mockTask(null, Exception(Constants.TEST_SOME_ERROR))
				}

				suspend fun coEveryAnswersGetUserFavorites(
								auth: FirebaseAuth,
								db: FirebaseFirestore,
				): List<String> {
								val userId = auth.currentUser?.uid ?: throw Exception(Constants.TEST_SOME_ERROR)

								val documentSnapshot = db
												.collection(FirebaseConstants.FIREBASE_USER)
												.document(userId)
												.get()
												.await()

								if (!documentSnapshot.exists()) throw FirebaseException.UserDocumentDoesNotExist()
								val favorites = documentSnapshot.get(FirebaseConstants.FIREBASE_FAVORITES) as? List<*>
								return favorites?.filterIsInstance<String>().orEmpty()
				}

				fun verifyDatabaseGetUserFavorites(db: FirebaseFirestore) = verify(exactly = 1) {
								db
												.collection(FirebaseConstants.FIREBASE_USER)
												.document(any<String>())
												.get()
				}
}