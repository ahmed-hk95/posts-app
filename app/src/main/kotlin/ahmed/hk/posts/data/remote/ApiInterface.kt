package ahmed.hk.posts.data.remote

import ahmed.hk.posts.data.models.Post

interface ApiInterface {

    suspend fun getPosts():List<Post>
}