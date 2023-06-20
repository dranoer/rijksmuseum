package com.dranoer.rijksmuseum.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dranoer.rijksmuseum.R

@Composable
fun DetailCard(
    modifier: Modifier = Modifier,
    alpha: Float,
    artist: String?,
    title: String?,
    description: String?,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .alpha(alpha),
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(
                top = 20.dp,
                start = 50.dp,
                end = 50.dp,
                bottom = 50.dp
            ),
        ) {
            //region Title
            Text(
                modifier = Modifier
                    .padding(top = 20.dp),
                text = title ?: "",
                style = MaterialTheme.typography.body1,
                color = colorResource(id = R.color.black),
            ) //endregion
            //region Description
            Text(
                modifier = Modifier
                    .padding(top = 14.dp),
                text = description ?: "",
                maxLines = 5,
                style = MaterialTheme.typography.subtitle1,
                color = colorResource(id = R.color.black),
            ) //endregion
            //region Artist
            Text(
                modifier = Modifier
                    .padding(top = 14.dp),
                text = "by $artist" ?: "",
                style = MaterialTheme.typography.caption,
                color = colorResource(id = R.color.black),
            ) //endregion
        }
    }
}

//region Preview
@Preview
@Composable
fun DetailCardPreview() =
    DetailCard(
        modifier = Modifier.requiredHeight(200.dp),
        alpha = 40F,
        artist = "an artist!",
        title = "Title 1",
        description = "This is a description for this artwork."
    )
//endregion