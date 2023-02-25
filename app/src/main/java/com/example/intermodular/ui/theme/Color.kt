package com.example.intermodular.ui.theme

import androidx.compose.ui.graphics.Color

// Light palette colors
val lightPrimary = Color(0xFF005F73)
val lightPrimaryVariant = Color(0x5B005F73)

val lightSecondary = Color(0xFF0A9396)
val lightSecondaryVariant = Color(0xFF10BCC0)

val lightBackground = Color(0xFFCA6702)
val lightSurface = Color(0xFFEE9B00)

val lightError = Color(0xFFB00020)

val lightOnPrimary = Color(0xFF001219)
val lightOnSecondary = Color(0xFF001219)
val lightOnBackground = Color(0xFF001219)
val lightOnSurface = Color(0xFF001219)
val lightOnError = Color(0xFFFFFFFF)


// Dark palette colors
val darkPrimary = Color(0xFF306F19)
val darkPrimaryVariant = Color(0xAE306F19)

val darkSecondary = Color(0xFF174052)
val darkSecondaryVariant = Color(0xFF002C3A)

val darkBackground = Color(0xFF18462A)
val darkSurface = Color(0xFF003300)

val darkError = Color(0xFFCF6679)

val darkOnPrimary = Color(0xFF9BE799)
val darkOnSecondary = Color(0xFF9BE799)
val darkOnBackground = Color(0xFF9BE799)
val darkOnSurface = Color(0xFF9BE799)
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