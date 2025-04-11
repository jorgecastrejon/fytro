package org.castre.fytro.features.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.hilt.navigation.compose.hiltViewModel
import org.castre.fytro.features.login.ui.LoginScreen
import org.castre.fytro.features.login.ui.LoginViewModel

const val LoginFeatureRoute = "login"

fun NavGraphBuilder.loginRoute(onSuccess: () -> Unit) {
    composable(LoginFeatureRoute) {
        val viewModel: LoginViewModel = hiltViewModel()

        LoginScreen(
            viewModel = viewModel,
            onSuccess = onSuccess
        )
    }
}