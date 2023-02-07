package com.ericggdev.beerfinderapp.data.source.remote

import com.ericggdev.beerfinderapp.data.entity.BeerEntity
import com.ericggdev.beerfinderapp.data.entity.toBeer
import com.ericggdev.beerfinderapp.data.source.apiHandler
import com.ericggdev.beerfinderapp.data.source.remote.api.BeerApi
import com.ericggdev.beerfinderapp.domain.models.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BeerRemoteDataSource @Inject constructor(
    private val beerApi: BeerApi
) {

    suspend fun getBeers(page: Int = 1): List<BeerEntity> = withContext(Dispatchers.IO) {
         apiHandler { beerApi.getBeers(page) }
    }
}