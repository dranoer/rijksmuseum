package com.dranoer.rijksmuseum.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.dranoer.rijksmuseum.R
import com.dranoer.rijksmuseum.networking.model.DetailItem
import com.dranoer.rijksmuseum.ui.theme.RijksmuseumTheme

@Composable
fun DetailView(item: DetailItem) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            //region Image
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = rememberAsyncImagePainter(item.imageUrl),
                contentDescription = stringResource(id = R.string.detail_image),
            ) //endregion
            //region Content
            DetailCardView(
                modifier = Modifier.requiredHeight(dimensionResource(id = R.dimen.size_500)),
                artist = item.artist,
                title = item.title,
                description = item.description,
            )
            //endregion
        }
    }
}

//region Preview
@Preview
@Composable
private fun HeaderView_Normal() {
    RijksmuseumTheme() {
        DetailView(
            DetailItem(
                id = "1",
                artist = "Artist 1",
                title = "Title 1",
                description = "This is a description for artwork number 1",
                imageUrl = "",
            )
        )
    }
}
//endregion