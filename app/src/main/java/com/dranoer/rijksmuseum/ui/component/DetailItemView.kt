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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.dranoer.rijksmuseum.ui.DetailItem
import com.dranoer.rijksmuseum.ui.theme.RijksmuseumTheme

@Composable
fun DetailItemView(item: DetailItem) {
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
                contentDescription = "Detail image",
            ) //endregion
            //region Content
            DetailCard(
                modifier = Modifier.requiredHeight(500.dp),
                alpha = 1F,
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
fun HeaderView_Normal() {
    RijksmuseumTheme() {
        DetailItemView(
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