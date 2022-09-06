package com.app.route

/**
 * @class Routes is used to define routes from main screen.
 * */
sealed class Routes(val route: String) {
    object Dashboard : Routes("Dashboard")
}