package com.ericggdev.beerfinderapp.data.source.remote.api


import com.ericggdev.beerfinderapp.data.entity.BeersResponseEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerApi {

    //GET BEERS
    @GET("/v2/beers")
    suspend fun getBeers(@Query("page") page: Int): Response<BeersResponseEntity>

    @GET("/v2/beers")
    suspend fun getBeersByName(@Query("beer_name") beerName: String): Response<BeersResponseEntity>
}