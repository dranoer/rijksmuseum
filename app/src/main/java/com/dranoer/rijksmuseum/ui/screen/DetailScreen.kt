package com.dranoer.rijksmuseum.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.dranoer.rijksmuseum.MainViewModel
import com.dranoer.rijksmuseum.MainViewModel.DetailUiState.Empty
import com.dranoer.rijksmuseum.MainViewModel.DetailUiState.Error
import com.dranoer.rijksmuseum.MainViewModel.DetailUiState.Loading
import com.dranoer.rijksmuseum.MainViewModel.DetailUiState.Success
import com.dranoer.rijksmuseum.R
import com.dranoer.rijksmuseum.ui.DetailItem
import com.dranoer.rijksmuseum.ui.component.DetailItemView
import com.dranoer.rijksmuseum.ui.theme.RijksmuseumTheme

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    id: String,
    backPress: () -> Unit,
) {
    fun launch() {
        viewModel.fetchArtDetail(id)
    }

    launch()

    LaunchedEffect(key1 = id) {
        viewModel.fetchArtDetail(objectNumber = id)
    }

    Surface(modifier = modifier.fillMaxSize()) {
        val state = viewModel.detailUiState.collectAsState().value
        //region UI State
        when (state) {
            is Empty -> Text(text = "Empty")

            is Loading -> Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) { CircularProgressIndicator() }

            is Success -> LoadedDetailScreen(detail = state.data, backPress = backPress)

            is Error -> Text(text = "Error ${state.message}")
        } //endregion
    }
}

@Composable
fun LoadedDetailScreen(
    detail: DetailItem?,
    backPress: () -> Unit,
) {
    Scaffold(
        //region TopAppBar
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { backPress() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.app_name),
                            tint = colorResource(id = R.color.black)
                        )
                    }
                },
                backgroundColor = colorResource(id = R.color.white),
                elevation = 0.dp,
            )
        }, //endregion
        //region Content
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp),
            ) {
                detail?.let { detailItem ->
                    DetailItemView(item = detailItem)
                }
            }
        } //endregion
    )
}

//region Preview
@Preview("Normal detail screen")
@Composable
private fun DetailPreview_Normal() {
    val navController = rememberNavController()
    RijksmuseumTheme {
        DetailScreen(id = "1", backPress = {})
    }
}
//endregion