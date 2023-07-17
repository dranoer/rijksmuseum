package com.dranoer.rijksmuseum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dranoer.rijksmuseum.ui.screen.DetailScreen
import com.dranoer.rijksmuseum.ui.screen.OverviewScreen
import com.dranoer.rijksmuseum.ui.theme.RijksmuseumTheme
import com.dranoer.rijksmuseum.ui.util.Const
import com.dranoer.rijksmuseum.ui.util.Route.Detail
import com.dranoer.rijksmuseum.ui.util.Route.Overview
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Due to the small scope of this app, the same instance of MainViewModel
     * is being shared between the OverviewScreen and DetailScreen.
     */
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RijksmuseumTheme {
                AppScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
private fun AppScreen(
    viewModel: MainViewModel,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Overview.route,
    ) {
        //region Overview Screen
        composable(route = Overview.route) {
            OverviewScreen(
                viewModel = viewModel,
                navigateToDetail = { id -> navController.navigate(Detail.createRoute(id = id)) },
            )
        } //endregion
        //region Detail Screen
        composable(
            route = Detail.route,
            arguments = listOf(
                navArgument(Const.DETAIL_ID) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val detailId = backStackEntry.arguments?.getString(Const.DETAIL_ID)
            requireNotNull(detailId) { stringResource(id = R.string.not_found) }
            DetailScreen(
                id = detailId,
                viewModel = viewModel,
                backPress = { navController.navigateUp() }
            )
        } //endregion
    }
}

//region Preview
@Preview(showBackground = true)
@Composable
private fun AppPreview() {
    RijksmuseumTheme {
        AppScreen(viewModel = hiltViewModel())
    }
}
//endregion