package com.ericggdev.beerfinderapp.domain.repository

import com.ericggdev.beerfinderapp.domain.models.Beer

interface BeerRepository {

    suspend fun getBeers(refresh: Boolean = true):List<Beer>

    suspend fun getBeer(id: Int): Beer
}