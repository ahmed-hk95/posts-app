package ahmed.hk.posts.data.repository

import ahmed.hk.posts.data.local.database.DatabaseRepository
import ahmed.hk.posts.data.models.Post
import ahmed.hk.posts.data.remote.NetworkRepository
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val networkRepository: NetworkRepository
) {

    fun getPosts(pageSize: Int): Flow<PagingData<Post>> {
        return databaseRepository.getPosts(pageSize)
    }
    suspend fun refreshPosts(){
        val posts = networkRepository.getAllPosts()
        databaseRepository.savePosts(posts)
    }
}