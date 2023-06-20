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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.dranoer.rijksmuseum.ui.ArtGroup
import com.dranoer.rijksmuseum.ui.ArtItem
import com.dranoer.rijksmuseum.ui.theme.RijksmuseumTheme
import com.dranoer.rijksmuseum.ui.util.OnClickListener

@Composable
fun ArtItem(artGroup: ArtGroup?, artItem: ArtItem?, callback: OnClickListener?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 18.dp)
            .clickable { artItem?.let { callback?.onClick(it) } },
        shape = RoundedCornerShape(16),
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp, top = 12.dp, end = 10.dp, bottom = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            //region Image
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    modifier = Modifier.size(80.dp),
                    painter = rememberAsyncImagePainter(artItem?.imageUrl ?: ""),
                    contentDescription = "Image url: ${artItem?.imageUrl ?: ""}",
                )
            } //endregion
            //region Spacer
            Spacer(modifier = Modifier.width(8.dp)) //endregion
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
                Spacer(modifier = Modifier.height(8.dp)) //endregion
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
private fun OverviewItem_Normal() {
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

        ArtItem(artGroup = artGroup, artItem = artItem, callback = null)
    }
}

@Preview
@Composable
private fun OverviewItemPreview_LongTitles() {
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

        ArtItem(artGroup = artGroup, artItem = artItem, callback = null)
    }
}

@Preview
@Composable
private fun OverviewItemPreview_NoItems() {
    RijksmuseumTheme {
        val artGroup = ArtGroup(
            author = "NoItems",
            artItems = emptyList()
        )

        ArtItem(artGroup = artGroup, artItem = null, callback = null)
    }
}
//endregion