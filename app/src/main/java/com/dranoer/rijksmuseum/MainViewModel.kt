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

    private val _overviewUiState = MutableStateFlow<OverviewUiState>(OverviewUiState.Loading)
    val overviewUiState: StateFlow<OverviewUiState> = _overviewUiState.asStateFlow()

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        fetchArts()
    }

    fun fetchArts(query: String = "") {
        viewModelScope.launch(dispatcher) {
            _isRefreshing.value = true
            try {
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
                _overviewUiState.value = OverviewUiState.Success(flow)
            } catch (ex: Exception) {
                _overviewUiState.value = OverviewUiState.Error(message = ex.message ?: "Unknown error")
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun fetchArtDetail(objectNumber: String) {
        viewModelScope.launch(dispatcher) {
            try {
                val response = repository.fetchArtDetail(id = objectNumber)
                _detailUiState.value = DetailUiState.Success(response)
            } catch (ex: Exception) {
                _detailUiState.value =
                    DetailUiState.Error(message = ex.message ?: "Unknown error")
            }
        }
    }

    sealed class OverviewUiState {
        object Loading : OverviewUiState()
        class Success(val data: Flow<PagingData<ArtGroup>>) : OverviewUiState()
        class Error(val message: String) : OverviewUiState() {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false
                other as Error
                if (message != other.message) return false
                return true
            }

            override fun hashCode(): Int {
                return message.hashCode()
            }
        }
    }

    sealed class DetailUiState {
        object Loading : DetailUiState()
        class Success(val data: DetailItem) : DetailUiState()
        class Error(val message: String) : DetailUiState() {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false
                other as Error
                if (message != other.message) return false
                return true
            }

            override fun hashCode(): Int {
                return message.hashCode()
            }
        }
    }
}