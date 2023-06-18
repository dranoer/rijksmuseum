package com.dranoer.rijksmuseum.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dranoer.rijksmuseum.R
import com.dranoer.rijksmuseum.ui.ArtItem
import com.dranoer.rijksmuseum.ui.component.DetailItem
import com.dranoer.rijksmuseum.ui.theme.RijksmuseumTheme

@Composable
fun DetailScreen(
    detail: ArtItem?,
    navController: NavController,
) {
    Scaffold(
        //region TopAppBar
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
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
                    DetailItem(item = detailItem)
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
        DetailScreen(
            detail = null,
            navController = navController,
        )
    }
}
//endregion