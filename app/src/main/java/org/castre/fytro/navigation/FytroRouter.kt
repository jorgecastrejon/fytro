package org.castre.fytro.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.castre.fytro.features.login.navigation.loginRoute

@Composable
fun FytroRouter (
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = FytroDestination.Login()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        loginRoute(onSuccess = {
            navController.navigate(FytroDestination.Login()) {
                popUpTo(FytroDestination.Login()) { inclusive = true }
            }
        })
    }
}