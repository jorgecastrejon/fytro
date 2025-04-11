package org.castre.fytro.features.login.ui

data class LoginState(
    val username: String = String(),
    val password: String = String(),
    val isLoading: Boolean = false,
    val isValid: Boolean = false,
)