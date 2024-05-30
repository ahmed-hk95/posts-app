package ahmed.hk.posts.data.remote

import ahmed.hk.posts.data.models.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val apiService: APIs) {

    suspend fun getAllPosts(): List<Post> =
        apiCall { apiService.getAllPosts() }

    suspend fun getPosts(pageNumber: Int): List<Post> {
        val limit = 10
        return apiCall { apiService.getPostsByPage(start = pageNumber*limit, limit = limit) }
    }
    private suspend fun <T> apiCall(apiCall: suspend () -> T): T {
        return withContext(Dispatchers.IO) {
            try {
                apiCall.invoke()
            }
            catch (exception: Exception){
                throw Exception("Something went wrong")
            }
        }
    }
}