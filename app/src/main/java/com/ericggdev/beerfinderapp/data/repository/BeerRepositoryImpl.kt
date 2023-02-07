package com.ericggdev.beerfinderapp.data.repository

import com.ericggdev.beerfinderapp.data.entity.toBeer
import com.ericggdev.beerfinderapp.data.source.remote.BeerRemoteDataSource
import com.ericggdev.beerfinderapp.domain.models.Beer
import com.ericggdev.beerfinderapp.domain.repository.BeerRepository
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(
    private val remoteDatasource: BeerRemoteDataSource
) : BeerRepository {

    override suspend fun getBeers(refresh: Boolean):List<Beer> {
        return remoteDatasource.getBeers()
            .map {
                it.toBeer()
            }
    }

    override suspend fun getBeer(id: Int): Beer {
        TODO("Not yet implemented")
    }

}