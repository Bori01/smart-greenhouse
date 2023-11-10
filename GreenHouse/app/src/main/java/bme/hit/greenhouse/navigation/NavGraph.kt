package bme.hit.greenhouse.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import bme.hit.greenhouse.feature.general.GeneralScreen
import bme.hit.greenhouse.feature.sector_check.CheckSectorScreen
import bme.hit.greenhouse.feature.sector_create.CreateSectorScreen
import bme.hit.greenhouse.feature.sector_list.SectorsScreen
import bme.hit.greenhouse.feature.sector_list.SectorsViewModel
import bme.hit.greenhouse.feature.settings.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Settings.route
    ) {
        composable(Screen.General.route) {
            GeneralScreen(
                onNavigateBack = {
                },
                onNavigateSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onNavigateSectors = {
                    navController.navigate(Screen.Sectors.route)
                }
            )
        }
        composable(Screen.Sectors.route) {
            SectorsScreen(
                onListItemClick = {
                    navController.navigate(Screen.CheckSector.passId(it))
                },
                onFabClick = {
                    navController.navigate(Screen.CreateSector.route)
                },
                onNavigateSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onNavigateGeneral = {
                    navController.navigate(Screen.General.route)
                }
            )
        }
        composable(Screen.CreateSector.route) {
            CreateSectorScreen(
                onNavigateBack = {
                    navController.popBackStack(
                        route = Screen.Sectors.route,
                        inclusive = true
                    )
                    navController.navigate(Screen.Sectors.route)
                }
            )
        }
        composable(
            route = Screen.CheckSector.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            CheckSectorScreen(
                onNavigateBack = {
                    navController.popBackStack(
                        route = Screen.Sectors.route,
                        inclusive = true
                    )
                    navController.navigate(Screen.Sectors.route)
                }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = {
                },
                onNavigateGeneral = {
                    navController.navigate(Screen.General.route)
                },
                onNavigateSectors = {
                    navController.navigate(Screen.Sectors.route)
                }
            )
        }
    }
}