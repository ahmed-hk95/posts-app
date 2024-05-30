package ahmed.hk.posts.di

import ahmed.hk.posts.data.local.database.PostDao
import ahmed.hk.posts.data.local.database.PostsDatabase
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePostsDatabase(@ApplicationContext context: Context): PostsDatabase =
        Room.databaseBuilder(context, PostsDatabase::class.java, "posts_database")
            .build()


    @Provides
    fun providePostsDao(postsDatabase: PostsDatabase): PostDao =
        postsDatabase.postsDao()
}