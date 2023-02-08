package com.ericggdev.beerfinderapp.domain.usecase

import com.ericggdev.beerfinderapp.domain.models.Beer
import com.ericggdev.beerfinderapp.domain.repository.BeerRepository
import com.ericggdev.beerfinderapp.domain.helpers.extension.resultOf
import javax.inject.Inject

class GetBeersUseCase @Inject constructor(
    private val beerRepository: BeerRepository
) {

    suspend operator fun invoke(page: Int = 1): Result<List<Beer>> = resultOf {
        beerRepository.getBeers(page)
    }
}