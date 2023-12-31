package com.dranoer.rijksmuseum.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dranoer.rijksmuseum.R

@Composable
fun DetailCardView(
    modifier: Modifier = Modifier,
    artist: String?,
    title: String?,
    description: String?,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        shape = RoundedCornerShape(
            topStart = dimensionResource(id = R.dimen.size_40),
            topEnd = dimensionResource(id = R.dimen.size_40)
        ),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.size_20),
                start = dimensionResource(id = R.dimen.size_50),
                end = dimensionResource(id = R.dimen.size_50),
                bottom = dimensionResource(id = R.dimen.size_50)
            ),
        ) {
            //region Title
            Text(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.size_20)),
                text = title ?: stringResource(id = R.string.empty_string),
                style = MaterialTheme.typography.body1,
                color = colorResource(id = R.color.black),
            ) //endregion
            //region Description
            Text(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.size_14)),
                text = description ?: "",
                maxLines = 5,
                style = MaterialTheme.typography.subtitle1,
                color = colorResource(id = R.color.black),
            ) //endregion
            //region Artist
            Text(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.size_14)),
                text = "by $artist",
                style = MaterialTheme.typography.caption,
                color = colorResource(id = R.color.black),
            ) //endregion
        }
    }
}

//region Preview
@Preview
@Composable
private fun DetailCardPreview_Normal() =
    DetailCardView(
        modifier = Modifier.requiredHeight(200.dp),
        artist = "an artist!",
        title = "Title 1",
        description = "This is a description for this artwork."
    )

@Preview
@Composable
private fun DetailCardPreview_LongDescription() =
    DetailCardView(
        modifier = Modifier.requiredHeight(200.dp),
        artist = "an artist!",
        title = "Title 1",
        description = "This is a very very very very very very very very very very very very very very very very very very very very very very long description for this artwork."
    )

@Preview
@Composable
private fun DetailCardPreview_MissingArtist() =
    DetailCardView(
        modifier = Modifier.requiredHeight(200.dp),
        artist = null,
        title = "Title 1",
        description = "This is a description for this artwork."
    )

@Preview
@Composable
private fun DetailCardPreview_LongArtistName() =
    DetailCardView(
        modifier = Modifier.requiredHeight(200.dp),
        artist = "This is a very very very very very very very very very very very very very very long artist name.",
        title = "Title 1",
        description = "This is a description for this artwork."
    )

@Preview
@Composable
private fun DetailCardPreview_MissingTitle() =
    DetailCardView(
        modifier = Modifier.requiredHeight(200.dp),
        artist = "an artist!",
        title = null,
        description = "This is a description for this artwork."
    )

@Preview
@Composable
private fun DetailCardPreview_LongTitle() =
    DetailCardView(
        modifier = Modifier.requiredHeight(200.dp),
        artist = "an artist!",
        title = "This is a very very very very very very very very very very very very very very very long title for this artwork.",
        description = "This is a description for this artwork."
    )

@Preview
@Composable
private fun DetailCardPreview_MissingDescription() =
    DetailCardView(
        modifier = Modifier.requiredHeight(200.dp),
        artist = "an artist!",
        title = "Title 1",
        description = null
    )
//endregion