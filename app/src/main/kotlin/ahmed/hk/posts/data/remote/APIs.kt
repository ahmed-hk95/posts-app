package ahmed.hk.posts.data.remote

import ahmed.hk.posts.data.models.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface APIs {

    @GET("/photos")
    suspend fun getPostsByPage(@Query("_start") start: Int, @Query ("_limit") limit: Int= 10):List<Post>

    @GET("/photos")
    suspend fun getAllPosts():List<Post>


}