package com.dranoer.rijksmuseum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dranoer.rijksmuseum.ui.screen.OverviewScreen
import com.dranoer.rijksmuseum.ui.theme.RijksmuseumTheme
import com.dranoer.rijksmuseum.ui.util.Route.Overview
import com.dranoer.rijksmuseum.ui.util.Route.Overview.route
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RijksmuseumTheme {
                AppScreen()
            }
        }
    }
}

@Composable
private fun AppScreen() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = route,
    ) {
        composable(route = Overview.route) {
            OverviewScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppPreview() {
    RijksmuseumTheme {
        AppScreen()
    }
}