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
    alpha: Float,
    artist: String?,
    title: String?,
    description: String?,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        shape = RoundedCornerShape(topStart = dimensionResource(id = R.dimen.size_40), topEnd = dimensionResource(id = R.dimen.size_40)),
        color = Color.White
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
private fun DetailCardPreview() =
    DetailCardView(
        modifier = Modifier.requiredHeight(200.dp),
        alpha = 40F,
        artist = "an artist!",
        title = "Title 1",
        description = "This is a description for this artwork."
    )
//endregion