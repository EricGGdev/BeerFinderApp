package com.ericggdev.beerfinderapp.domain.helpers.extension

import com.ericggdev.beerfinderapp.domain.models.ResultError

inline fun <R> resultOf(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (error: ResultError) {
        Result.failure(error)
    }
}