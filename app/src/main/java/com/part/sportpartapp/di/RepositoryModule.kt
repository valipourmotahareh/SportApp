package com.part.sportpartapp.di

import com.part.sportpartapp.sportList.data.repository.SportListRepositoryImpl
import com.part.sportpartapp.sportList.domain.repository.SportListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class  RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSportListRepository(
        sportListRepositoryImpl: SportListRepositoryImpl
    ): SportListRepository
}