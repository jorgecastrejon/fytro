package org.castre.fytro.features.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    private val _onEvent = MutableSharedFlow<LoginEvent>()
    val onEvent = _onEvent.asSharedFlow()

    fun onUsernameChange(username: String) {
        update {
            copy(
                username = username,
                isValid = username.isNotBlank() && password.isNotBlank()
            )
        }
    }

    fun onPasswordChanged(password: String) {
        update {
            copy(
                password = password,
                isValid = username.isNotBlank() && password.isNotBlank()
            )
        }
    }

    fun onButtonClicked() {
        viewModelScope.launch(Dispatchers.Default) {
            update { copy(isLoading = true) }
            //
            update { copy(isLoading = false) }
        }
    }

    private fun update(updateOp: LoginState.() -> LoginState) {
        _uiState.value = _uiState.value.updateOp()
    }

}