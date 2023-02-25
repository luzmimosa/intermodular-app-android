package com.example.intermodular.ui.theme

import androidx.compose.ui.graphics.Color

// Light palette colors
val lightPrimary = Color(0xFF168AAD)
val lightPrimaryVariant = Color(0x5B168AAD)

val lightSecondary = Color(0xFF0A9396)
val lightSecondaryVariant = Color(0xFF10BCC0)

val lightBackground = Color(0xFF184E77)
val lightSurface = Color(0xFF168AAD)


val lightError = Color(0xFFB00020)

val lightOnPrimary = Color(0xFF001219)
val lightOnSecondary = Color(0xFF001219)
val lightOnBackground = Color(0xFF001219)
val lightOnSurface = Color(0xFF001219)
val lightOnError = Color(0xFFFFFFFF)


// Dark palette colors
val darkPrimary = Color(0xFF1D2C4D)
val darkPrimaryVariant = Color(0x5B072D38)

val darkSecondary = Color(0xFF033738)
val darkSecondaryVariant = Color(0xFF074D4E)

val darkBackground = Color(0xFF0A2133)
val darkSurface = Color(0xFF083542)

val darkError = Color(0xFFCF6679)

val darkOnPrimary = Color(0xFF5BB4D6)
val darkOnSecondary = Color(0xFF5BB4D6)
val darkOnBackground = Color(0xFF5BB4D6)
val darkOnSurface = Color(0xFF5BB4D6)
val darkOnError = Color(0xFF1B251B)


interface RouteDifficultyColors {
    val trivial: Color
    val easy: Color
    val medium: Color
    val hard: Color
    val expert: Color
}

// Light route difficulty colors
object RouteDifficultyLightColors : RouteDifficultyColors {
    override val trivial = Color(0xFFCDFFB8)
    override val easy = Color(0xFFB2FF59)
    override val medium = Color(0xFFEEFF41)
    override val hard = Color(0xFFFF0059)
    override val expert = Color(0xFF420000)

}

// Dark route difficulty colors
object RouteDifficultyDarkColors : RouteDifficultyColors {
    override val trivial = Color(0xFF4C5C58)
    override val easy = Color(0xFF357E1A)
    override val medium = Color(0xFF967F0D)
    override val hard = Color(0xFF700619)
    override val expert = Color(0xFF000000)
}