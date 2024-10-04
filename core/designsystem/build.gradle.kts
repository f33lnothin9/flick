plugins {
    alias(libs.plugins.flick.android.library)
    alias(libs.plugins.flick.android.library.compose)
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "ru.resodostudio.flick.core.designsystem"
}

dependencies {
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.util)

    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.coil.kt.compose)
}