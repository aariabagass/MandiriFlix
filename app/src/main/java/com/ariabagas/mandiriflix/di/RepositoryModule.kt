package com.ariabagas.mandiriflix.di

import com.ariabagas.mandiriflix.data.repository.MovieRepositoryImpl
import com.ariabagas.mandiriflix.core.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindMovieRepository(repository: MovieRepositoryImpl): MovieRepository
}