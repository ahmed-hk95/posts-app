package ahmed.hk.posts.data.local.database

import ahmed.hk.posts.data.models.Post
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Post::class], version = 1)
abstract class PostsDatabase: RoomDatabase() {

    abstract fun postsDao(): PostDao
}