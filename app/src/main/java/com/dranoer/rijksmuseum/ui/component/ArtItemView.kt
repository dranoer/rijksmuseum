package com.dranoer.rijksmuseum.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dranoer.rijksmuseum.ui.ArtGroup
import com.dranoer.rijksmuseum.ui.ArtItem
import com.dranoer.rijksmuseum.ui.theme.RijksmuseumTheme

@Composable
fun ArtItem(artItems: ArtGroup) {
    for (item in artItems.artItems) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            backgroundColor = Color.LightGray,
            shape = RoundedCornerShape(16),
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Column {
                    Text(text = artItems.author)
                    Divider(
                        color = Color.Black,
                        thickness = 1.dp,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                    Text(text = item.title, style = TextStyle(fontWeight = FontWeight.Bold))
                    Text(text = item.longTitle)
                }
            }
        }
    }
}

//region Preview
@Preview
@Composable
private fun ArtPreview_Normal() {
    RijksmuseumTheme {
        val artItem = ArtItem(
            id = "1",
            artist = "Artist 1",
            title = "Title 1",
            longTitle = "This is a description for artwork number 1",
            imageUrl = "",
            headerImageUrl = ""
        )

        val artGroup = ArtGroup(
            author = "Normal",
            artItems = listOf(artItem)
        )

        ArtItem(artItems = artGroup)
    }
}

@Preview
@Composable
private fun ArtItemPreview_LongTitles() {
    RijksmuseumTheme {
        val artItem = ArtItem(
            id = "1",
            artist = "Artist 1",
            title = "Title 1",
            longTitle = "This is a very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very long title.",
            imageUrl = "",
            headerImageUrl = ""
        )
        val artGroup = ArtGroup(
            author = "LongTitles",
            artItems = listOf(artItem)
        )
        ArtItem(artItems = artGroup)
    }
}

@Preview
@Composable
private fun ArtItemPreview_NoItems() {
    RijksmuseumTheme {
        val artGroup = ArtGroup(
            author = "NoItems",
            artItems = emptyList()
        )
        ArtItem(artItems = artGroup)
    }
}
//endregion