package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CyberColorScheme = darkColorScheme(
  primary = CyberCyan,
  onPrimary = CyberDarkVoid,
  primaryContainer = CyberSurface,
  onPrimaryContainer = CyberCyan,
  secondary = CyberEmerald,
  onSecondary = CyberDarkVoid,
  secondaryContainer = CyberCardBg,
  onSecondaryContainer = CyberEmerald,
  tertiary = CyberPurple,
  background = CyberDarkVoid,
  onBackground = CyberTextPrimary,
  surface = CyberDarkBg,
  onSurface = CyberTextPrimary,
  surfaceVariant = CyberCardBg,
  onSurfaceVariant = CyberTextSecondary,
  outline = CyberBorder,
  error = CyberCrimson,
  onError = CyberDarkVoid,
)

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = CyberColorScheme,
    typography = Typography,
    content = content
  )
}
