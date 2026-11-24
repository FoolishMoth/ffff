package com.example.workoutreminder.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PinkPrimary,
    onPrimary = DarkOnPrimary,
    secondary = PinkSecondary,
    tertiary = PinkTertiary,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface
)

private val LightColorScheme = lightColorScheme(
    primary = PinkPrimary,
    onPrimary = DarkOnPrimary,
    secondary = PinkSecondary,
    tertiary = PinkTertiary
)

@Composable
fun WorkoutReminderTheme(
    darkTheme: Boolean = true, // Принудительно темная тема для соответствия запросу
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
