package org.castre.fytro.features.login.ui

sealed class LoginEvent {
    data object Succeed: LoginEvent()
    data class Error(val error: Any): LoginEvent()
}