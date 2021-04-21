package today.pathos.companyb.codingtest.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import today.pathos.companyb.codingtest.data.local.db.DbFavoriteRepository
import today.pathos.companyb.codingtest.data.remote.NetworkUserRepository
import today.pathos.companyb.codingtest.domain.repository.FavoriteRepository
import today.pathos.companyb.codingtest.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindUserRepository(
        userRepository: NetworkUserRepository
    ): UserRepository

    @Binds
    abstract fun bindFavoriteRepository(
        favoriteRepository: DbFavoriteRepository
    ): FavoriteRepository


}