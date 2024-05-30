package ahmed.hk.posts.data.repository

import ahmed.hk.posts.data.local.database.DatabaseRepository
import ahmed.hk.posts.data.models.Post
import ahmed.hk.posts.data.remote.NetworkRepository
import androidx.paging.PagingData
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostsRepositoryTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private lateinit var postsRepository: PostsRepository

    private val databaseRepository = mockk<DatabaseRepository>()
    private val networkRepository = mockk<NetworkRepository>()
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)
        postsRepository = PostsRepository(databaseRepository, networkRepository)
    }
    @AfterEach
    fun clear() {
        clearAllMocks()
        Dispatchers.resetMain()
        dispatcher.cancel()
    }
    @Test
    fun `getPosts() should return posts paging data from the PostsDao`() = runTest {
        // Arrange
        val postsList = listOf(Post(1, "Title 1", "Body 1"))
        val pagingFlow = flowOf(PagingData.from(postsList))
        coEvery { databaseRepository.getPosts() } returns pagingFlow

        // Act
        val result = postsRepository.getPosts(10)

        verify { databaseRepository.getPosts(10) }

        // Assert
        result.collect {
            Assertions.assertEquals(it,pagingFlow)
        }
    }
}