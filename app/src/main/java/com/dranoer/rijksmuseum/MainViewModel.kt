package com.dranoer.rijksmuseum

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dranoer.rijksmuseum.data.remote.CoroutineDispatcherProvider
import com.dranoer.rijksmuseum.domain.ArtRepository
import com.dranoer.rijksmuseum.ui.ArtGroup
import com.dranoer.rijksmuseum.ui.DetailItem
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val _overviewUiState = MutableStateFlow<OverviewUiState>(OverviewUiState.Empty)
    val overviewUiState: StateFlow<OverviewUiState> = _overviewUiState.asStateFlow()

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Empty)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        fetchArts()
    }

    fun fetchArts(query: String = "") {
        viewModelScope.launch {
            _isRefreshing.value = true
            _overviewUiState.value = OverviewUiState.Loading

            launch(coroutineDispatcherProvider.IO()) {
                try {
                    Log.d("MainViewModel", "Fetch arts started at ${System.currentTimeMillis()}")
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
                    Log.d("MainViewModel", "Data fetched at ${System.currentTimeMillis()}")

                    _overviewUiState.value = OverviewUiState.Success(flow)
                } catch (ex: Exception) {
                    _overviewUiState.value =
                        OverviewUiState.Error(message = ex.message ?: "Unknown error")
                } finally {
                    _isRefreshing.value = false
                }
            }
        }
    }

    fun fetchArtDetail(objectNumber: String) {
        viewModelScope.launch {
            _detailUiState.value = DetailUiState.Loading

            launch(coroutineDispatcherProvider.IO()) {
                try {
                    val response = repository.fetchArtDetail(id = objectNumber)
                    _detailUiState.value = DetailUiState.Success(response)
                    Log.d("naz", "response in detail screen is: ${response.title}")
                } catch (ex: Exception) {
                    _detailUiState.value =
                        DetailUiState.Error(message = ex.message ?: "Unknown error")
                }
            }
        }
    }

    sealed class OverviewUiState {
        object Empty : OverviewUiState()
        object Loading : OverviewUiState()
        class Success(val data: Flow<PagingData<ArtGroup>>) : OverviewUiState()
        class Error(val message: String) : OverviewUiState()
    }

    sealed class DetailUiState {
        object Empty : DetailUiState()
        object Loading : DetailUiState()
        class Success(val data: DetailItem) : DetailUiState()
        class Error(val message: String) : DetailUiState()
    }
}