package ahmed.hk.posts.data.remote

import ahmed.hk.posts.data.models.Post
import retrofit2.http.GET

interface APIs {

    @GET("/v3/8c1211bc-7a66-48cc-a992-c7e7f1eb5b24")
    suspend fun getPosts():List<Post>
}