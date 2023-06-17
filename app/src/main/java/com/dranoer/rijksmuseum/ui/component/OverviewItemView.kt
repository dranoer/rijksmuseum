package com.dranoer.rijksmuseum.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dranoer.rijksmuseum.ui.ArtGroup
import com.dranoer.rijksmuseum.ui.ArtItem
import com.dranoer.rijksmuseum.ui.theme.RijksmuseumTheme

@Composable
fun OverviewItem(artItems: List<ArtGroup>) {
    LazyColumn(
        modifier = Modifier.padding(6.dp),
    ) {
        items(
            items = artItems,
            itemContent = { item ->
                ArtItem(artItems = item)
            }
        )
    }
}

@Preview
@Composable
private fun OverviewItemPreview_SingleGroup() {
    RijksmuseumTheme {
        val artItem1 = ArtItem(
            id = "1",
            artist = "Artist 1",
            title = "Title 1",
            longTitle = "This is a description for artwork number 1",
            imageUrl = "",
            headerImageUrl = ""
        )
        val artItem2 = ArtItem(
            id = "2",
            artist = "Artist 2",
            title = "Title 2",
            longTitle = "This is a description for artwork number 2",
            imageUrl = "",
            headerImageUrl = ""
        )
        val artGroup = ArtGroup(
            author = "Art Group 1",
            artItems = listOf(artItem1, artItem2)
        )
        OverviewItem(artItems = listOf(artGroup))
    }
}

@Preview
@Composable
private fun OverviewItemPreview_MultipleGroups() {
    RijksmuseumTheme {
        val artItem1 = ArtItem(
            id = "1",
            artist = "Artist 1",
            title = "Title 1",
            longTitle = "This is a description for artwork number 1",
            imageUrl = "",
            headerImageUrl = ""
        )
        val artItem2 = ArtItem(
            id = "2",
            artist = "Artist 2",
            title = "Title 2",
            longTitle = "This is a description for artwork number 2",
            imageUrl = "",
            headerImageUrl = ""
        )
        val artGroup1 = ArtGroup(
            author = "Art Group 1",
            artItems = listOf(artItem1, artItem2)
        )
        val artItem3 = ArtItem(
            id = "3",
            artist = "Artist 3",
            title = "Title 3",
            longTitle = "This is a description for artwork number 3",
            imageUrl = "",
            headerImageUrl = ""
        )
        val artItem4 = ArtItem(
            id = "4",
            artist = "Artist 4",
            title = "Title 4",
            longTitle = "This is a description for artwork number 4",
            imageUrl = "",
            headerImageUrl = ""
        )
        val artGroup2 = ArtGroup(
            author = "Art Group 2",
            artItems = listOf(artItem3, artItem4)
        )
        OverviewItem(artItems = listOf(artGroup1, artGroup2))
    }
}
//endregion