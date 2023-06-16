package com.dranoer.rijksmuseum.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dranoer.rijksmuseum.ui.theme.RijksmuseumTheme

@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Content(modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
) {
}

//region Preview
@Preview
@Composable
private fun OverviewPreview() {
    RijksmuseumTheme {
        Content()
    }
}