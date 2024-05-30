package ahmed.hk.posts.data.repository

import ahmed.hk.posts.data.local.database.DatabaseRepository
import ahmed.hk.posts.data.remote.NetworkRepository
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val networkRepository: NetworkRepository
) {

}