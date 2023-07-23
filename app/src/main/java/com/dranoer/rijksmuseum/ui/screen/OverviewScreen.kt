package com.dranoer.rijksmuseum.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.dranoer.rijksmuseum.MainViewModel
import com.dranoer.rijksmuseum.MainViewModel.OverviewUiState.Error
import com.dranoer.rijksmuseum.MainViewModel.OverviewUiState.Loading
import com.dranoer.rijksmuseum.MainViewModel.OverviewUiState.Success
import com.dranoer.rijksmuseum.R
import com.dranoer.rijksmuseum.networking.model.ArtGroup
import com.dranoer.rijksmuseum.networking.model.ArtItem
import com.dranoer.rijksmuseum.ui.component.ArtView
import com.dranoer.rijksmuseum.ui.component.ErrorView
import com.dranoer.rijksmuseum.ui.theme.RijksmuseumTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navigateToDetail: (String) -> Unit,
) {
    val state = viewModel.overviewUiState.collectAsState().value

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
                //region UI State
                when (state) {
                    is Loading -> {
                        if (!state.isRefreshing) CircularProgressIndicator()
                    }

                    is Success -> {
                        val lazyPagingItems = state.data.collectAsLazyPagingItems()

                        SwipeRefresh(
                            state = rememberSwipeRefreshState(isRefreshing = state.isRefreshing),
                            onRefresh = { viewModel.fetchArts() },
                        ) {
                            if (lazyPagingItems.itemCount > 0) {
                                LoadedOverviewScreen(
                                    pagingItems = lazyPagingItems,
                                    navigateToDetail = navigateToDetail
                                )
                            } else if (!state.isRefreshing) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    is Error -> {
                        if (!state.isRefreshing) ErrorView(message = state.message, viewModel::fetchArts)
                    }
                } //endregion
            }
        }, //endregion
    )
}

@Composable
private fun LoadedOverviewScreen(
    pagingItems: LazyPagingItems<ArtGroup>,
    navigateToDetail: (String) -> Unit,
) {
    LazyColumn(modifier = Modifier.padding(20.dp, 2.dp, 20.dp, 24.dp)) {
        items(pagingItems) { artGroup ->
            artGroup?.artItems?.forEach { artItem ->
                ArtView(artGroup, artItem) {
                    navigateToDetail(it.objectNumber)
                }
            }
        }
    }
}

//region Preview
@Preview
@Composable
private fun OverviewPreview_SingleGroup() {
    RijksmuseumTheme {
        val artItem1 = ArtItem(
            id = "1",
            objectNumber = "1",
            artist = "Artist 1",
            title = "Title 1",
            description = "This is a description for artwork number 1",
            imageUrl = "",
            headerImageUrl = ""
        )
        val artItem2 = ArtItem(
            id = "2",
            objectNumber = "2",
            artist = "Artist 2",
            title = "Title 2",
            description = "This is a description for artwork number 2",
            imageUrl = "",
            headerImageUrl = ""
        )
        val artGroup = ArtGroup(
            author = "Art Group 1",
            artItems = listOf(artItem1, artItem2)
        )

        // A fake Overview composable that doesn't use paging
        OverviewNonPaged(artGroups = listOf(artGroup), artItem = artItem1, onItemClicked = { })
    }
}

@Preview
@Composable
private fun OverviewPreview_MultipleGroups() {
    RijksmuseumTheme {
        val artItem1 = ArtItem(
            id = "1",
            objectNumber = "1",
            artist = "Artist 1",
            title = "Title 1",
            description = "This is a description for artwork number 1",
            imageUrl = "",
            headerImageUrl = ""
        )
        val artItem2 = ArtItem(
            id = "2",
            objectNumber = "2",
            artist = "Artist 2",
            title = "Title 2",
            description = "This is a description for artwork number 2",
            imageUrl = "",
            headerImageUrl = ""
        )
        val artGroup1 = ArtGroup(
            author = "Art Group 1",
            artItems = listOf(artItem1, artItem2)
        )
        val artItem3 = ArtItem(
            id = "3",
            objectNumber = "3",
            artist = "Artist 3",
            title = "Title 3",
            description = "This is a description for artwork number 3",
            imageUrl = "",
            headerImageUrl = ""
        )
        val artItem4 = ArtItem(
            id = "4",
            objectNumber = "4",
            artist = "Artist 4",
            title = "Title 4",
            description = "This is a description for artwork number 4",
            imageUrl = "",
            headerImageUrl = ""
        )
        val artGroup2 = ArtGroup(
            author = "Art Group 2",
            artItems = listOf(artItem3, artItem4)
        )

        OverviewNonPaged(
            artGroups = listOf(artGroup1, artGroup2),
            artItem = artItem1,
            onItemClicked = { },
        )
    }
}

@Composable
private fun OverviewNonPaged(
    artGroups: List<ArtGroup>,
    artItem: ArtItem,
    onItemClicked: (ArtItem) -> Unit
) {
    LazyColumn {
        items(artGroups) { group ->
            ArtView(artGroup = group, artItem = artItem, onItemClicked = onItemClicked)
        }
    }
}

@Preview
@Composable
private fun OverviewPreview_Loading() {
    RijksmuseumTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
private fun OverviewPreview_Error() {
    RijksmuseumTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "Error: Something went wrong")
        }
    }
}
//endregion