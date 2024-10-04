plugins {
    alias(libs.plugins.flick.android.library)
    alias(libs.plugins.flick.android.library.compose)
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "ru.resodostudio.flick.core.ui"
}

dependencies {
    api(projects.core.designsystem)
    api(projects.core.model)

    implementation(libs.coil.kt.compose)
    implementation(libs.lottie.compose)
}
