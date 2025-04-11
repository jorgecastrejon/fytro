package org.castre.fytro.navigation

import org.castre.fytro.features.login.navigation.LoginFeatureRoute

sealed class FytroDestination {
    data object Login : FytroDestination() {
        operator fun invoke(): String = LoginFeatureRoute
    }
}