package org.castre.fytro

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.castre.fytro.designsystem.FytroTheme
import org.castre.fytro.navigation.FytroRouter

@Composable
fun FytroApp(
    entryPoint: String,
    dynamicColor: Boolean = true,
) {
    FytroTheme(
        dynamicColor = dynamicColor,
    ) {
        val navController = rememberNavController()
        FytroRouter(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            navController = navController,
            startDestination = entryPoint
        )
    }
}