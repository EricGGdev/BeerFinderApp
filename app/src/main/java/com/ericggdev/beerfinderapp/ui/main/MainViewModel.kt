package com.ericggdev.beerfinderapp.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericggdev.beerfinderapp.domain.models.Empty
import com.ericggdev.beerfinderapp.domain.models.ResultError
import com.ericggdev.beerfinderapp.domain.models.Beer
import com.ericggdev.beerfinderapp.domain.usecase.GetBeersByNameUseCase
import com.ericggdev.beerfinderapp.domain.usecase.GetBeersUseCase
import com.ericggdev.beerfinderapp.helpers.Constants.PAGE_MAX_VALUE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BeersUiState(
    val items: List<Beer> = emptyList(),
    val isLoading: Boolean = false,
    val error: ResultError = Empty
)

@HiltViewModel
class MainViewModel  @Inject constructor(
    private val getBeersUseCase: GetBeersUseCase,
    private val getBeersByNameUseCase: GetBeersByNameUseCase
): ViewModel() {

    val uiState by lazy {
        MutableStateFlow(BeersUiState(isLoading = true))
    }
    private var lastPageLoaded = 1
    var isCallInProgress = false
    var isFloatingDeployed = false
    init {
        getInitialBeers()
    }

    fun getInitialBeers() {
        setLoadingState(true)
        lastPageLoaded = 1
        viewModelScope.launch {
            val result = getBeersUseCase()
            result.onSuccess { list ->
                setSuccessState(list)
            }
            result.onFailure {error ->
            }
        }
    }

    fun findBeerByName(text:String) {
        setLoadingState(true)
        lastPageLoaded = 1
        viewModelScope.launch {
            val result = getBeersByNameUseCase(text)
            result.onSuccess { list ->
                setSuccessState(list)
            }
            result.onFailure {error ->
            }
        }
    }
    fun getNextBeersPage(){
        if( lastPageLoaded < PAGE_MAX_VALUE){
            isCallInProgress = true
            lastPageLoaded = lastPageLoaded.inc()
            val alreadyLoadedBeers = uiState.value.items
            viewModelScope.launch {
                val result = getBeersUseCase(lastPageLoaded)
                result.onSuccess { list ->
                    setSuccessState(alreadyLoadedBeers + list)
                    isCallInProgress = false
                }
                result.onFailure { error ->
                    Log.e("asdas","asdas")
                    isCallInProgress = false
                }
            }
        }
    }
    private fun setSuccessState(list: List<Beer>) {
        uiState.update {
            it.copy(
                items = list,
                isLoading = false,
                error = Empty
            )
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        uiState.update {
            it.copy(
                isLoading = isLoading
            )
        }
    }



}
