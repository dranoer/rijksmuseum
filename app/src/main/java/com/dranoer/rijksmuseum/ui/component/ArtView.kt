package com.dranoer.rijksmuseum.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.dranoer.rijksmuseum.R
import com.dranoer.rijksmuseum.ui.ArtGroup
import com.dranoer.rijksmuseum.ui.ArtItem
import com.dranoer.rijksmuseum.ui.theme.RijksmuseumTheme

@Composable
fun ArtView(artGroup: ArtGroup?, artItem: ArtItem?, onItemClicked: (ArtItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.size_18))
            .clickable { artItem?.let { onItemClicked(it) } },
        shape = RoundedCornerShape(16),
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.size_12),
                    top = dimensionResource(id = R.dimen.size_12),
                    end = dimensionResource(id = R.dimen.size_10),
                    bottom = dimensionResource(id = R.dimen.size_12)
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            //region Image
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    modifier = Modifier.size(dimensionResource(id = R.dimen.size_80)),
                    painter = rememberAsyncImagePainter(artItem?.imageUrl ?: ""),
                    contentDescription = "Image url: ${artItem?.imageUrl ?: ""}",
                )
            } //endregion
            //region Spacer
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.size_8))) //endregion
            //region Info
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                //region Header
                Text(
                    text = artGroup?.author ?: "",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                ) //endregion
                //region Spacer
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.size_8))) //endregion
                //region Title
                Text(
                    text = artItem?.title ?: "",
                    style = TextStyle(fontSize = 14.sp)
                ) //endregion
            } //endregion
        }
    }
}

//region Preview
@Preview
@Composable
private fun ArtView_Normal() {
    RijksmuseumTheme {
        val artItem = ArtItem(
            id = "1",
            objectNumber = "1",
            artist = "Artist 1",
            title = "Title 1",
            description = "This is a description for artwork number 1",
            imageUrl = "",
            headerImageUrl = ""
        )
        val artGroup = ArtGroup(
            author = "Normal",
            artItems = listOf(artItem)
        )

        ArtView(artGroup = artGroup, artItem = artItem, onItemClicked = {})
    }
}

@Preview
@Composable
private fun ArtViewPreview_LongTitles() {
    RijksmuseumTheme {
        val artItem = ArtItem(
            id = "1",
            objectNumber = "1",
            artist = "Artist 1",
            title = "Title 1",
            description = "This is a very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very long title.",
            imageUrl = "",
            headerImageUrl = ""
        )
        val artGroup = ArtGroup(
            author = "LongTitles",
            artItems = listOf(artItem)
        )

        ArtView(artGroup = artGroup, artItem = artItem, onItemClicked = {})
    }
}

@Preview
@Composable
private fun ArtViewPreview_NoItems() {
    RijksmuseumTheme {
        val artGroup = ArtGroup(
            author = "NoItems",
            artItems = emptyList()
        )

        ArtView(artGroup = artGroup, artItem = null, onItemClicked = {})
    }
}
//endregion