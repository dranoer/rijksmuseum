package com.dranoer.rijksmuseum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dranoer.rijksmuseum.data.remote.CoroutineDispatcherProvider
import com.dranoer.rijksmuseum.domain.ArtRepository
import com.dranoer.rijksmuseum.ui.ArtGroup
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

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        fetchArts()
    }

    fun fetchArts(query: String = "") {
        viewModelScope.launch {
            _isRefreshing.value = true
            _uiState.value = UiState.Loading

            launch(coroutineDispatcherProvider.IO()) {
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

                    _uiState.value = UiState.Success(flow)
                } catch (ex: Exception) {
                    _uiState.value = UiState.Error(message = ex.message ?: "Unknown error")
                } finally {
                    _isRefreshing.value = false
                }
            }
        }
    }

//    fun getItemById(itemId: String): ArtItem? {
//        return when (val state = uiState.value) {
//            is UiState.Success -> {
//                state.data
//                    .flatMap { it.artItems }
//                    .find { it.id == itemId }
//            }
//
//            else -> null
//        }
//    }

    sealed class UiState {
        object Empty : UiState()
        object Loading : UiState()
        class Success(val data: Flow<PagingData<ArtGroup>>) : UiState()
        class Error(val message: String) : UiState()
    }
}