package com.dranoer.rijksmuseum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dranoer.rijksmuseum.domain.ArtRepository
import com.dranoer.rijksmuseum.ui.ArtGroup
import com.dranoer.rijksmuseum.ui.DetailItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ArtRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _overviewUiState = MutableStateFlow<OverviewUiState>(OverviewUiState.Loading(isRefreshing = false))
    val overviewUiState: StateFlow<OverviewUiState> = _overviewUiState.asStateFlow()

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading(isRefreshing = false))
    val detailUiState: StateFlow<DetailUiState> = _detailUiState.asStateFlow()

    init {
        fetchArts()
    }

    fun fetchArts(query: String = "") {
        viewModelScope.launch(dispatcher) {
            _overviewUiState.value = OverviewUiState.Loading(isRefreshing = true)

            val result = try {
                val flow = repository.fetchArtList(query)
                    .map { pagingData ->
                        pagingData.map { artItem ->
                            ArtGroup(
                                author = artItem.artist,
                                artItems = listOf(artItem)
                            )
                        }
                    }
                    .cachedIn(viewModelScope)
                OverviewUiState.Success(flow)
            } catch (ex: Exception) {
                OverviewUiState.Error(message = ex.message ?: "Unknown error")
            }

            _overviewUiState.value = when (result) {
                is OverviewUiState.Success -> result.copy(isRefreshing = false)
                is OverviewUiState.Error -> result.copy(isRefreshing = false)
                else -> result
            }
        }
    }

    fun fetchArtDetail(objectNumber: String) {
        viewModelScope.launch(dispatcher) {
            _detailUiState.value = DetailUiState.Loading(isRefreshing = true)
            val result = try {
                DetailUiState.Success(data = repository.fetchArtDetail(id = objectNumber))
            } catch (ex: Exception) {
                DetailUiState.Error(message = ex.message ?: "Unknown error")
            }

            _detailUiState.value = when (result) {
                is DetailUiState.Success -> result.copy(isRefreshing = false)
                is DetailUiState.Error -> result.copy(isRefreshing = false)
                else -> result
            }
        }
    }

    sealed class OverviewUiState {
        data class Loading(val isRefreshing: Boolean = false) : OverviewUiState()
        data class Success(val data: Flow<PagingData<ArtGroup>>, val isRefreshing: Boolean = false) : OverviewUiState()
        data class Error(val message: String, val isRefreshing: Boolean = false) : OverviewUiState()
    }

    sealed class DetailUiState {
        data class Loading(val isRefreshing: Boolean = false) : DetailUiState()
        data class Success(val data: DetailItem, val isRefreshing: Boolean = false) : DetailUiState()
        data class Error(val message: String, val isRefreshing: Boolean = false) : DetailUiState()
    }
}