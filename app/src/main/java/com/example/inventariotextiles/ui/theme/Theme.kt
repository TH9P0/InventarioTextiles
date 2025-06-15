package com.example.inventariotextiles.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2A3C4B),      // Azul oscuro (texto principal, íconos)
    onPrimary = Color.White,          // Texto sobre botones primarios
    primaryContainer = Color(0xFFBFD4E3), // Fondo para elementos primarios

    secondary = Color(0xFF7D8A94),    // Gris azulado secundario
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFDCE4E9), // Fondo para chips, botones secundarios

    background = Color(0xFFF4F1EC),   // Fondo general claro (beige)
    onBackground = Color(0xFF2A3C4B), // Texto sobre fondo

    surface = Color(0xFFFFFFFF),      // Fondo de tarjetas o superficies elevadas
    onSurface = Color(0xFF2A3C4B),    // Texto sobre superficie

    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002)
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF9FC2D6),      // Azul suavizado para fondo oscuro
    onPrimary = Color(0xFF1B1B1B),    // Texto sobre botones primarios
    primaryContainer = Color(0xFF2A3C4B), // Fondo del contenedor principal

    secondary = Color(0xFFAAB4BE),    // Gris azulado más claro
    onSecondary = Color(0xFF1B1B1B),
    secondaryContainer = Color(0xFF4D5860), // Fondo de elementos secundarios

    background = Color(0xFF1B1B1B),   // Fondo general oscuro
    onBackground = Color(0xFFE2E2E2), // Texto claro sobre fondo oscuro

    surface = Color(0xFF2A2A2A),      // Tarjetas o superficies oscuras
    onSurface = Color(0xFFF4F1EC),    // Texto claro

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6)
)

@Composable
fun InventarioTextilesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}