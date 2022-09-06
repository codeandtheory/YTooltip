package com.app.route

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.ui.screen.dashboard.Dashboard


/**
 * @Composable MainNavigation is used to handle navigation from main screen.
 * Here we are handling navigation according to route names.
 * */

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Dashboard.route) {
        composable(Routes.Dashboard.route) {
            Dashboard(navController = navController)
        }
    }
}