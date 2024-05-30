package ahmed.hk.posts.data.local.database

import ahmed.hk.posts.data.models.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val postsDao: PostDao) {
    suspend fun savePosts(posts: List<Post>) {
        postsDao.clearPosts()
        postsDao.insertPosts(posts)
    }

    fun getPosts(): Flow<List<Post>> =
        postsDao.getAll()

}