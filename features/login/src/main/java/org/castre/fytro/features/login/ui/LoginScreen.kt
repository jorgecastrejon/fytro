package org.castre.fytro.features.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import org.castre.fytro.features.login.R
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
    onSuccess: () -> Unit = {}
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val events = remember(viewModel.onEvent, lifecycleOwner) {
        viewModel.onEvent.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    LaunchedEffect (true) {
        events.collectLatest { event ->
            when (event) {
                is LoginEvent.Succeed -> onSuccess()
                is LoginEvent.Error -> {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar("Error")
                    }
                }
            }
        }
    }

    LoginContent(
        modifier = modifier,
        loginState = state,
        onUserChange = remember { viewModel::onUsernameChange  },
        onPasswordChange = remember { viewModel::onPasswordChanged },
        onButtonClick = remember { viewModel::onButtonClicked }
    )

    SnackbarHost(hostState = snackBarHostState) { data ->
        Snackbar(
            modifier = Modifier.padding(vertical = 24.dp),
            snackbarData = data,
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError
        )
    }
}

@Composable
private fun LoginContent(
    modifier: Modifier = Modifier,
    loginState: LoginState,
    onUserChange : (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column (
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .background(MaterialTheme.colorScheme.background)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { focusManager.clearFocus() },
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.login_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(32.dp))
        val keyboardController = LocalSoftwareKeyboardController.current

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = loginState.username,
            onValueChange = onUserChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            textStyle = MaterialTheme.typography.bodyMedium,
            label = { Text(text = stringResource(id = R.string.login_user_label)) },
            placeholder = { Text(text = stringResource(id = R.string.login_user_placeholder)) },
        )

        Spacer(modifier = Modifier.height(8.dp))
        var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = loginState.password,
            onValueChange = onPasswordChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            visualTransformation = if (!isPasswordVisible) PasswordVisualTransformation() else None,
            textStyle = MaterialTheme.typography.bodyMedium,
            label = { Text(text = stringResource(id = R.string.login_password_label)) },
            placeholder = { Text(text = stringResource(id = R.string.login_password_placeholder)) },
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            enabled = !loginState.isLoading,
                            indication = null,
                            onClick = { isPasswordVisible = !isPasswordVisible }
                        ),
                    imageVector = if (isPasswordVisible) {
                        Icons.Filled.VisibilityOff
                    } else {
                        Icons.Filled.Visibility
                    },
                    contentDescription = "toggle password visibility"
                )
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        val onClick: () -> Unit = remember { {
            keyboardController?.hide()
            focusManager.clearFocus()
            onButtonClick()
        }}

        Button (
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = loginState.isValid && loginState.isLoading.not(),
            onClick = onClick
        ) {
            Text(
                text = stringResource(id = R.string.login_button),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
