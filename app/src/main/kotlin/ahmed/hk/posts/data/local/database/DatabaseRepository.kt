package ahmed.hk.posts.data.local.database

import ahmed.hk.posts.data.models.Post
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val postsDao: PostDao) {
    suspend fun savePosts(posts: List<Post>) {
        postsDao.clearPosts()
        postsDao.insertPosts(posts)
    }

    suspend fun getPostDetails(id: Int): Post? =
        postsDao.getPostDetails(id)

    fun getPosts(pageSize: Int = 10): Flow<PagingData<Post>> =
        Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { postsDao.getAll() }
        ).flow
}