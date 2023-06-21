package com.dranoer.rijksmuseum.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dranoer.rijksmuseum.R
import com.dranoer.rijksmuseum.ui.theme.RijksmuseumTheme

@Composable
fun ErrorView(
    message: String,
    refresh: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(message)
        Spacer(Modifier.height(dimensionResource(id = R.dimen.size_16)))
        Button(onClick = refresh) { Text(text = stringResource(id = R.string.retry)) }
    }
}

//region Preview
@Preview("Normal Error view")
@Composable
private fun PreviewErrorView_Normal() {
    RijksmuseumTheme() {
        ErrorView(
            message = "Oops! there is something wrong..",
            refresh = {}
        )
    }
} //endregion