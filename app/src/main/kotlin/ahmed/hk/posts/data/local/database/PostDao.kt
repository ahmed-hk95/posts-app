package ahmed.hk.posts.data.local.database

import ahmed.hk.posts.data.models.Post
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Post>)

    @Query("SELECT * FROM posts WHERE id = :id")
    suspend fun getPostDetails(id: Int): Post?

    @Query("SELECT * FROM posts ORDER BY id ASC")
    fun getAll(): PagingSource<Int,Post>

    @Query("DELETE FROM posts")
    suspend fun clearPosts()
}