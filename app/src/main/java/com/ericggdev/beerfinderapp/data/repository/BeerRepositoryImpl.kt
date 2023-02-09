package com.ericggdev.beerfinderapp.data.repository

import com.ericggdev.beerfinderapp.data.entity.toBeer
import com.ericggdev.beerfinderapp.data.source.remote.BeerRemoteDataSource
import com.ericggdev.beerfinderapp.domain.models.Beer
import com.ericggdev.beerfinderapp.domain.repository.BeerRepository
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(
    private val remoteDatasource: BeerRemoteDataSource
) : BeerRepository {

    override suspend fun getBeers(page: Int): List<Beer> {
        return remoteDatasource.getBeers(page)
            .map {
                it.toBeer()
            }
    }
    override suspend fun getBeersByName(beerName: String): List<Beer> {
        return remoteDatasource.getBeersByName(beerName)
            .map {
                it.toBeer()
            }
    }

}