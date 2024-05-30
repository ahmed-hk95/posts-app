package ahmed.hk.posts.data.remote

import ahmed.hk.posts.data.models.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: APIs):ApiInterface {
    override suspend fun getPosts(): List<Post> =
        apiCall { apiService.getPosts() }

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