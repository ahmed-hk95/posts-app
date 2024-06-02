package ahmed.hk.posts.data.repository

import ahmed.hk.posts.data.local.database.DatabaseRepository
import ahmed.hk.posts.data.models.Post
import ahmed.hk.posts.data.remote.NetworkRepository
import androidx.paging.PagingData
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
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
        val pagingData = PagingData.from(listOf(Post(1, "Title 1", "Body 1")))
        val pagingFlow = flowOf(pagingData)
        coEvery { databaseRepository.getPosts() } returns pagingFlow

        // Act
        val result = postsRepository.getPosts(10)

        // Assert
        result.collect {
            Assertions.assertEquals(it,pagingData)
        }
    }

    @Test
    fun `getPostDetails(postId) should return post details from the PostsDao`() = runTest{
        // Arrange
        val postId = 1
        val post = Post(postId, "Title 1", "Body 1")
        coEvery { databaseRepository.getPostDetails(postId) } returns post

        // Act
        val result = postsRepository.getPostDetails(postId)

        // Assert
        Assertions.assertEquals(result, post)
    }

    @Test
    fun `refreshPosts() should refresh posts from the NetworkRepository`() = runTest {
        // Arrange
        val posts = listOf(Post(1, "Title 1", "Body 1"), Post(2, "Title 2", "Body 2"))

        coEvery { networkRepository.getAllPosts() } returns posts
        coEvery { databaseRepository.savePosts(posts) } just Runs

        //Act
        postsRepository.refreshPosts()

        coVerify { networkRepository.getAllPosts() }
        coVerify { databaseRepository.savePosts(posts) }
    }
}