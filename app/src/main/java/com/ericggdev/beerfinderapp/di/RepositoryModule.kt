package com.ericggdev.beerfinderapp.di

import com.ericggdev.beerfinderapp.data.repository.BeerRepositoryImpl
import com.ericggdev.beerfinderapp.domain.repository.BeerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsBeerRepository(repositoryImpl: BeerRepositoryImpl) : BeerRepository
}