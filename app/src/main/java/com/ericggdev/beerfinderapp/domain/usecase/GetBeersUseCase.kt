package com.ericggdev.beerfinderapp.domain.usecase

import com.ericggdev.beerfinderapp.domain.models.Beer
import com.ericggdev.beerfinderapp.domain.repository.BeerRepository
import com.ericggdev.beerfinderapp.helpers.extensions.resultOf
import javax.inject.Inject

class GetBeersUseCase @Inject constructor(
    private val beerRepository: BeerRepository
) {

    suspend operator fun invoke(): Result<List<Beer>> = resultOf {
         beerRepository.getBeers(false)
    }
}