plugins {
    alias(libs.plugins.flick.android.feature)
    alias(libs.plugins.flick.android.library.compose)
}

android {
    namespace = "ru.resodostudios.flick.feature.favorites"
}

dependencies {
    implementation(libs.androidx.hilt.navigation.compose)
}
