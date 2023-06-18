package com.dranoer.rijksmuseum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dranoer.rijksmuseum.ui.screen.DetailScreen
import com.dranoer.rijksmuseum.ui.screen.OverviewScreen
import com.dranoer.rijksmuseum.ui.theme.RijksmuseumTheme
import com.dranoer.rijksmuseum.ui.util.Route.Detail
import com.dranoer.rijksmuseum.ui.util.Route.Overview
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
    val viewModel: MainViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Overview.route,
    ) {
        //region Overview Screen
        composable(route = Overview.route) {
            OverviewScreen(navController = navController)
        } //endregion

        //region Detail Screen
        composable(route = "${Detail.route}/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            val selectedItem = itemId?.let { viewModel.getItemById(it) }
            DetailScreen(navController = navController, detail = selectedItem)
        } //endregion
    }
}

//region Preview
@Preview(showBackground = true)
@Composable
private fun AppPreview() {
    RijksmuseumTheme {
        AppScreen()
    }
}
//endregion