package `in`.tarun.gallery.core.di

import `in`.tarun.gallery.feature.gallerydetails.data.repository.MediaRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.tarun.gallery.feature.gallerydetails.domain.repository.MediaRepository
import `in`.tarun.gallery.feature.galleryview.data.MediaDetailsRepoImp
import `in`.tarun.gallery.feature.galleryview.domain.MediaDetailsRepo
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMediaRepos(
        mediaRepositoryImpl: MediaRepositoryImpl
    ): MediaRepository

    @Binds
    @Singleton
    abstract fun bindMediaDetailsRepo(
        mediaDetailsRepoImpl: MediaDetailsRepoImp
    ): MediaDetailsRepo
}