package com.dranoer.rijksmuseum.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.dranoer.rijksmuseum.ui.ArtItem

@Composable
fun DetailItem(item: ArtItem) {
    Column {
        //region Avatar
        Image(
            modifier = Modifier.size(40.dp),
            painter = rememberAsyncImagePainter(item.imageUrl),
            contentDescription = null,
        ) //endregion
        Spacer(modifier = Modifier.width(10.dp))
        //region Name
        Text(
            text = item.id,
            color = Color.Black,
            style = MaterialTheme.typography.h6,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        ) //endregion
        Spacer(modifier = Modifier.width(10.dp))
        //region Name
        Text(
            text = item.longTitle,
            color = Color.Black,
            style = MaterialTheme.typography.h6,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        ) //endregion
    }
}
