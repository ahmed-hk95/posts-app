package ahmed.hk.posts.di

import ahmed.hk.posts.data.local.database.DatabaseRepository
import ahmed.hk.posts.data.remote.NetworkRepository
import ahmed.hk.posts.data.repository.PostsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun providePostsRepository(databaseRepository: DatabaseRepository,networkRepository: NetworkRepository): PostsRepository {
        return PostsRepository(databaseRepository, networkRepository)
    }

}