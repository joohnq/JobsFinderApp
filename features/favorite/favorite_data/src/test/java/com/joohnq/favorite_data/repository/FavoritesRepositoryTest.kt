import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.common.truth.Truth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.joohnq.core_test.mockk.firebase.FirebaseUserGetMockkHelper
import com.joohnq.core_test.mockk.firebase.FirebaseUserUpdateMockkHelper
import com.joohnq.favorite_data.repository.FavoritesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class FavoritesRepositoryTest {
				@get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()
				private lateinit var favoritesRepository: FavoritesRepository
				private lateinit var auth: FirebaseAuth
				private lateinit var db: FirebaseFirestore
				private val id: String = "1"
				private val userId: String = "2"
				private lateinit var taskCompletionSourceVoid: TaskCompletionSource<Void>
				private lateinit var taskCompletionSourceDocumentSnapshot: TaskCompletionSource<DocumentSnapshot>
				private lateinit var firebaseUserUpdateMockkHelper: FirebaseUserUpdateMockkHelper
				private lateinit var firebaseUserGetMockkHelper: FirebaseUserGetMockkHelper

				@Before
				fun setUp() {
								taskCompletionSourceVoid = TaskCompletionSource<Void>()
								taskCompletionSourceDocumentSnapshot = TaskCompletionSource<DocumentSnapshot>()
								auth = mockk { every { currentUser?.uid } returns userId }
								firebaseUserUpdateMockkHelper = FirebaseUserUpdateMockkHelper(taskCompletionSourceVoid)
								firebaseUserGetMockkHelper =
												FirebaseUserGetMockkHelper(taskCompletionSourceDocumentSnapshot)
				}

				@Test
				fun `test adding item to favorites, should return true`() {
								val documentReferenceMock: DocumentReference =
												firebaseUserUpdateMockkHelper.createDocumentReferenceUpdateUser()
								val collectionReferenceMock: CollectionReference =
												firebaseUserUpdateMockkHelper.createCollectionReference(documentReferenceMock)
								db = firebaseUserUpdateMockkHelper.createFirebaseFirestoreUser(collectionReferenceMock)
								favoritesRepository = spyk(FavoritesRepository(auth, db), recordPrivateCalls = true)

								firebaseUserUpdateMockkHelper.everyDatabaseUpdateUserFavorites(db)

								coEvery { favoritesRepository.add(any()) } answers {
												firebaseUserUpdateMockkHelper.coEveryAnswersUpdateUserFavorites(this, auth, db, true)
								}

								val res = runBlocking { favoritesRepository.add(id) }

								firebaseUserUpdateMockkHelper.verifyDatabaseUpdateUserFavorites(db)
								coVerify(exactly = 1) { favoritesRepository.add(id) }
								Truth.assertThat(res).isTrue()
				}

				@Test
				fun `test adding item to favorites, should return false`() {
								val documentReferenceMock: DocumentReference =
												firebaseUserUpdateMockkHelper.createDocumentReferenceUpdateUserException()
								val collectionReferenceMock: CollectionReference =
												firebaseUserUpdateMockkHelper.createCollectionReference(documentReferenceMock)
								db = firebaseUserUpdateMockkHelper.createFirebaseFirestoreUser(collectionReferenceMock)
								favoritesRepository = spyk(FavoritesRepository(auth, db), recordPrivateCalls = true)

								firebaseUserUpdateMockkHelper.everyDatabaseUpdateUserFavoritesException(db)

								val res = runBlocking { favoritesRepository.add(id) }
								firebaseUserUpdateMockkHelper.verifyDatabaseUpdateUserFavorites(db)
								coVerify { favoritesRepository.add(any()) }
								Truth.assertThat(res).isFalse()
				}

				@Test
				fun `test removing item to favorites, should return true`() {
								val documentReferenceMock: DocumentReference =
												firebaseUserUpdateMockkHelper.createDocumentReferenceUpdateUser()
								val collectionReferenceMock: CollectionReference =
												firebaseUserUpdateMockkHelper.createCollectionReference(documentReferenceMock)
								db = firebaseUserUpdateMockkHelper.createFirebaseFirestoreUser(collectionReferenceMock)
								favoritesRepository = spyk(FavoritesRepository(auth, db), recordPrivateCalls = true)

								firebaseUserUpdateMockkHelper.everyDatabaseUpdateUserFavorites(db)

								coEvery { favoritesRepository.remove(any()) } answers {
												firebaseUserUpdateMockkHelper.coEveryAnswersUpdateUserFavorites(this, auth, db, false)
								}

								val res = runBlocking { favoritesRepository.remove(id) }
								firebaseUserUpdateMockkHelper.verifyDatabaseUpdateUserFavorites(db)
								coVerify { favoritesRepository.remove(any()) }
								Truth.assertThat(res).isTrue()
				}

				@Test
				fun `test removing item to favorites, should return false`() {
								val documentReferenceMock: DocumentReference =
												firebaseUserUpdateMockkHelper.createDocumentReferenceUpdateUserException()
								val collectionReferenceMock: CollectionReference =
												firebaseUserUpdateMockkHelper.createCollectionReference(documentReferenceMock)
								db = firebaseUserUpdateMockkHelper.createFirebaseFirestoreUser(collectionReferenceMock)
								favoritesRepository = spyk(FavoritesRepository(auth, db), recordPrivateCalls = true)

								firebaseUserUpdateMockkHelper.everyDatabaseUpdateUserFavoritesException(db)

								val res = runBlocking { favoritesRepository.remove(id) }
								firebaseUserUpdateMockkHelper.verifyDatabaseUpdateUserFavorites(db)
								coVerify { favoritesRepository.remove(any()) }
								Truth.assertThat(res).isFalse()
				}

				@Test
				fun `test fetching favorites successfully, should return a list of string`() {
								val favoritesList = listOf("1", "2")
								val documentReferenceMock: DocumentReference =
												firebaseUserGetMockkHelper.createDocumentReferenceGetUser()
								val collectionReferenceMock: CollectionReference =
												firebaseUserGetMockkHelper.createCollectionReference(documentReferenceMock)
								db = firebaseUserGetMockkHelper.createFirebaseFirestoreUser(collectionReferenceMock)
								favoritesRepository = spyk(FavoritesRepository(auth, db), recordPrivateCalls = true)

								val snapshot: DocumentSnapshot =
												firebaseUserGetMockkHelper.everyDatabaseGetUserFavorites(db)

								taskCompletionSourceDocumentSnapshot.setResult(snapshot)

								every { snapshot.exists() } returns true
								every { snapshot.get(any<String>()) } returns favoritesList

								coEvery { favoritesRepository.fetch() } coAnswers {
												firebaseUserGetMockkHelper.coEveryAnswersGetUserFavorites(auth, db)
								}

								val res = runBlocking { favoritesRepository.fetch() }
								firebaseUserGetMockkHelper.verifyDatabaseGetUserFavorites(db)
								coVerify { favoritesRepository.fetch() }
								Truth.assertThat(res).isNotEmpty()
								Truth.assertThat(res).isEqualTo(favoritesList)
				}

				@Test
				fun `test fetching favorites, should return a empty list of string`() {
								val favoritesList = emptyList<String>()
								val documentReferenceMock: DocumentReference =
												firebaseUserGetMockkHelper.createDocumentReferenceGetUser()
								val collectionReferenceMock: CollectionReference =
												firebaseUserGetMockkHelper.createCollectionReference(documentReferenceMock)
								db = firebaseUserGetMockkHelper.createFirebaseFirestoreUser(collectionReferenceMock)
								favoritesRepository = spyk(FavoritesRepository(auth, db), recordPrivateCalls = true)

								val snapshot: DocumentSnapshot =
												firebaseUserGetMockkHelper.everyDatabaseGetUserFavorites(db)

								taskCompletionSourceDocumentSnapshot.setResult(snapshot)

								every { snapshot.exists() } returns true
								every { snapshot.get(any<String>()) } returns favoritesList

								coEvery { favoritesRepository.fetch() } coAnswers {
												firebaseUserGetMockkHelper.coEveryAnswersGetUserFavorites(auth, db)
								}

								val res = runBlocking { favoritesRepository.fetch() }
								firebaseUserGetMockkHelper.verifyDatabaseGetUserFavorites(db)
								coVerify { favoritesRepository.fetch() }
								Truth.assertThat(res).isEmpty()
								Truth.assertThat(res).isEqualTo(favoritesList)
				}

				@Test(expected = Exception::class)
				fun `test fetching favorites failure, should return a exception`() {
								val documentReferenceMock: DocumentReference =
												firebaseUserGetMockkHelper.createDocumentReferenceGetUserException()
								val collectionReferenceMock: CollectionReference =
												firebaseUserGetMockkHelper.createCollectionReference(documentReferenceMock)
								db = firebaseUserGetMockkHelper.createFirebaseFirestoreUser(collectionReferenceMock)
								favoritesRepository = spyk(FavoritesRepository(auth, db), recordPrivateCalls = true)

								runBlocking { favoritesRepository.fetch() }
								firebaseUserGetMockkHelper.verifyDatabaseGetUserFavorites(db)
								coVerify { favoritesRepository.fetch() }
				}

}
