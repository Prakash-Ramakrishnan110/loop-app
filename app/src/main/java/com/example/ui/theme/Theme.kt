package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = LoopPrimaryLight,
    onPrimary = Color.White,
    primaryContainer = LoopPrimaryDark,
    onPrimaryContainer = Color.White,
    secondary = LoopAccent,
    onSecondary = Color.Black,
    tertiary = LoopSuccess,
    background = LoopDarkBackground,
    onBackground = LoopDarkTextPrimary,
    surface = LoopDarkCard,
    onSurface = LoopDarkTextPrimary,
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = LoopDarkTextSecondary,
    outline = LoopDarkBorder,
    error = LoopError,
    onError = Color.White
  )

private val LightColorScheme =
  lightColorScheme(
    primary = LoopPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE0F2FE),
    onPrimaryContainer = LoopPrimaryDark,
    secondary = LoopAccent,
    onSecondary = Color.White,
    tertiary = LoopSuccess,
    background = LoopBackground,
    onBackground = LoopTextPrimary,
    surface = LoopCard,
    onSurface = LoopTextPrimary,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = LoopTextSecondary,
    outline = LoopBorder,
    error = LoopError,
    onError = Color.White
  )

@Composable
fun LoopTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  LoopTheme(darkTheme = darkTheme, content = content)
}

