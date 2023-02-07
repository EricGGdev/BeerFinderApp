package com.ericggdev.beerfinderapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericggdev.beerfinderapp.domain.models.Empty
import com.ericggdev.beerfinderapp.domain.models.ResultError
import com.ericggdev.beerfinderapp.domain.models.Beer
import com.ericggdev.beerfinderapp.domain.usecase.GetBeersUseCase
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
    private val getBeersUseCase: GetBeersUseCase
): ViewModel() {

    val uiState by lazy {
        MutableStateFlow(BeersUiState(isLoading = true))
    }
    var isFloatingDeployed = false
    init {
        viewModelScope.launch {
           val result = getBeersUseCase()
            result.onSuccess { list ->
                setSuccessState(list)
            }
            result.onFailure {error ->
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

}
