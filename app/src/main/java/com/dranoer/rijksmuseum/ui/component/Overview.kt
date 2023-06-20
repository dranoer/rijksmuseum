package com.dranoer.rijksmuseum.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.dranoer.rijksmuseum.ui.ArtGroup
import com.dranoer.rijksmuseum.ui.ArtItem
import com.dranoer.rijksmuseum.ui.theme.RijksmuseumTheme
import com.dranoer.rijksmuseum.ui.util.OnClickListener

@Composable
fun Overview(
    lazyPagingItems: LazyPagingItems<ArtGroup>,
    callback: OnClickListener?,
) {
    LazyColumn(
        modifier = Modifier.padding(start = 20.dp, top = 2.dp, end = 20.dp, bottom = 24.dp),
    ) {
        items(lazyPagingItems) { artGroup ->
            artGroup?.artItems?.forEach { artItem ->
                OverviewItem(artGroup, artItem, callback)
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
        OverviewNonPaged(artGroups = listOf(artGroup), artItem = artItem1, onItemClick = null)
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
            onItemClick = null
        )
    }
}

@Composable
fun OverviewNonPaged(artGroups: List<ArtGroup>, artItem: ArtItem, onItemClick: OnClickListener?) {
    LazyColumn {
        items(artGroups) { group ->
            OverviewItem(artGroup = group, artItem = artItem, callback = onItemClick)
        }
    }
}
//endregion