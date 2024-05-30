package ahmed.hk.posts.data.local.database

import ahmed.hk.posts.data.models.Post
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Post>)

    @Query("SELECT * FROM posts")
    fun getAll(): Flow<List<Post>>

    @Query("DELETE FROM posts")
    suspend fun clearPosts()
}