package com.dranoer.rijksmuseum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dranoer.rijksmuseum.data.remote.CoroutineDispatcherProvider
import com.dranoer.rijksmuseum.domain.ArtRepository
import com.dranoer.rijksmuseum.ui.ArtGroup
import com.dranoer.rijksmuseum.ui.ArtItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ArtRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        fetchArts()
    }

    fun fetchArts() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _isRefreshing.value = true

            launch(coroutineDispatcherProvider.IO()) {
                viewModelScope.launch(coroutineDispatcherProvider.IO()) {
                    try {
                        val artItemList = repository.fetchArtList()
                        val artGroups = artItemList.groupBy { it.artist }.map {
                            ArtGroup(author = it.key, artItems = it.value)
                        }
                        _uiState.value = UiState.Success(artGroups)
                    } catch (ex: Exception) {
                        _uiState.value = UiState.Error(ex.message ?: "Unknown error")
                    } finally {
                        _isRefreshing.value = false
                    }
                }
            }
        }
    }

    fun getItemById(itemId: String): ArtItem? {
        return when (val state = uiState.value) {
            is UiState.Success -> {
                state.data
                    .flatMap { it.artItems }
                    .find { it.id == itemId }
            }

            else -> null
        }
    }

    sealed class UiState {
        object Empty : UiState()
        object Loading : UiState()
        class Success(val data: List<ArtGroup>) : UiState()
        class Error(val message: String) : UiState()
    }
}