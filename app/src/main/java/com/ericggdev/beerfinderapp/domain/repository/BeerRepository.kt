package com.ericggdev.beerfinderapp.domain.repository

import com.ericggdev.beerfinderapp.domain.models.Beer

interface BeerRepository {

    suspend fun getBeers(page: Int = 1): List<Beer>

    suspend fun getBeersByName(beerName: String): List<Beer>
}