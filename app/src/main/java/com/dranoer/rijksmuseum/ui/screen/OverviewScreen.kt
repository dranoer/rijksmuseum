package com.dranoer.rijksmuseum.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.dranoer.rijksmuseum.MainViewModel
import com.dranoer.rijksmuseum.MainViewModel.UiState.Empty
import com.dranoer.rijksmuseum.MainViewModel.UiState.Error
import com.dranoer.rijksmuseum.MainViewModel.UiState.Loading
import com.dranoer.rijksmuseum.MainViewModel.UiState.Success
import com.dranoer.rijksmuseum.R
import com.dranoer.rijksmuseum.ui.ArtGroup
import com.dranoer.rijksmuseum.ui.component.OverviewItem
import com.dranoer.rijksmuseum.ui.theme.RijksmuseumTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState.collectAsState().value

    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        //region UI State
        when (state) {
            is Empty -> Text(text = "Empty")

            is Loading -> Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) { CircularProgressIndicator() }

            is Success ->
                LoadedScreen(
                    modifier = Modifier.fillMaxSize(),
                    artGroups = state.data,
                    viewModel = viewModel,
                )

            is Error -> Text(text = "Error ${state.message}")
        } //endregion
    }
}

@Composable
fun LoadedScreen(artGroups: List<ArtGroup>, modifier: Modifier, viewModel: MainViewModel) {
    Scaffold(
        //region TopAppBar
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        },
        //endregion
        //region Content
        content = { padding ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(color = colorResource(id = R.color.gray_50)),
                contentAlignment = Alignment.Center,
            ) {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = viewModel.isRefreshing.collectAsState().value),
                    onRefresh = { viewModel.fetchArts() },
                ) {
                    OverviewItem(artItems = artGroups)
                }
            }
        },
        //endregion
    )
}

//region Preview
@Preview
@Composable
private fun OverviewPreview_Normal() {
    RijksmuseumTheme {
        OverviewScreen()
    }
}
//endregion