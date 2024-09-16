import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.tasks.OnCompleteListener
import com.google.common.truth.Truth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.getField
import com.joohnq.core.constants.Constants
import com.joohnq.core.constants.FirebaseConstants
import com.joohnq.core_test.mockk.mockTask
import com.joohnq.favorite_data.repository.FavoritesRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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

				@Before
				fun setUp() {
								auth = mockk { every { currentUser?.uid } returns userId }
				}

				@Test
				fun `test adding item to favorites, should return true`() = runTest {
								auth = mockk(relaxed = true) { every { currentUser?.uid } returns userId }
								db = mockk(relaxed = true)
								favoritesRepository = FavoritesRepository(auth, db)

								val task = mockTask<Void>(null)

								every { task.addOnCompleteListener(any()) } answers {
												firstArg<OnCompleteListener<Void>>().onComplete(task)
												task
								}

								every {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any())
																.update(any<String>(), any())
								} returns task

								val res = favoritesRepository.add(id)

								verify(exactly = 1) {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any<String>())
																.update(any<String>(), any())
								}

								Truth.assertThat(res).isNotNull()
								Truth.assertThat(res).isTrue()
				}

				@Test(expected = Exception::class)
				fun `test adding item to favorites, should return an exception`() = runTest {
								val exception = Exception(Constants.TEST_SOME_ERROR)
								auth = mockk(relaxed = true) { every { currentUser?.uid } returns userId }
								db = mockk(relaxed = true)
								favoritesRepository = FavoritesRepository(auth, db)

								val task = mockTask<Void>(null, exception)

								every { task.addOnCompleteListener(any()) } answers {
												firstArg<OnCompleteListener<Void>>().onComplete(task)
												task
								}

								every {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any())
																.update(any<String>(), any())
								} returns task

								favoritesRepository.add(id)

								verify(exactly = 1) {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any<String>())
																.update(any<String>(), any())
								}
				}

				@Test
				fun `test removing item to favorites, should return true`() = runTest {
								auth = mockk(relaxed = true) { every { currentUser?.uid } returns userId }
								db = mockk(relaxed = true)
								favoritesRepository = FavoritesRepository(auth, db)

								val task = mockTask<Void>(null)

								every { task.addOnCompleteListener(any()) } answers {
												firstArg<OnCompleteListener<Void>>().onComplete(task)
												task
								}

								every {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any())
																.update(any<String>(), any())
								} returns task

								val res = favoritesRepository.remove(id)

								verify(exactly = 1) {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any<String>())
																.update(any<String>(), any())
								}

								Truth.assertThat(res).isNotNull()
								Truth.assertThat(res).isTrue()
				}

				@Test(expected = Exception::class)
				fun `test removing item to favorites, should return an exception`() = runTest {
								val exception = Exception(Constants.TEST_SOME_ERROR)
								auth = mockk(relaxed = true) { every { currentUser?.uid } returns userId }
								db = mockk(relaxed = true)
								favoritesRepository = FavoritesRepository(auth, db)

								val task = mockTask<Void>(null, exception)

								every { task.addOnCompleteListener(any()) } answers {
												firstArg<OnCompleteListener<Void>>().onComplete(task)
												task
								}

								every {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any())
																.update(any<String>(), any())
								} returns task

								favoritesRepository.add(id)

								verify(exactly = 1) {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any<String>())
																.update(any<String>(), any())
								}
				}

				@Test
				fun `test fetching favorites successfully, should return a list of string`() = runTest {
								val favoritesList = listOf("1", "2", "3")
								auth = mockk(relaxed = true) { every { currentUser?.uid } returns userId }
								db = mockk(relaxed = true)
								favoritesRepository = FavoritesRepository(auth, db)

								val documentSnapshot = mockk<DocumentSnapshot>(relaxed = true) {
												every {
																(getField(FirebaseConstants.FIREBASE_FAVORITES) as? List<*>)?.filterIsInstance<String>()
																				.orEmpty()
												} returns favoritesList
								}
								val task = mockTask<DocumentSnapshot>(documentSnapshot)

								every { task.addOnCompleteListener(any()) } answers {
												firstArg<OnCompleteListener<DocumentSnapshot>>().onComplete(task)
												task
								}

								every {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any())
																.get()
								} returns task

								val list = favoritesRepository.fetch()

								verify(exactly = 1) {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any<String>())
																.get()
								}

								Truth.assertThat(list).isNotNull()
								Truth.assertThat(list).isNotEmpty()
								Truth.assertThat(list).isEqualTo(favoritesList)
				}

				@Test(expected = Exception::class)
				fun `test fetching favorites, should return an exception`() = runTest {
								val exception = Exception(Constants.TEST_SOME_ERROR)
								auth = mockk(relaxed = true) { every { currentUser?.uid } returns userId }
								db = mockk(relaxed = true)
								favoritesRepository = FavoritesRepository(auth, db)

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

								favoritesRepository.fetch()

								verify(exactly = 1) {
												db.collection(FirebaseConstants.FIREBASE_USER)
																.document(any<String>())
																.get()
								}
				}
}
