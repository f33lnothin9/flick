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
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    //api(libs.androidx.compose.ui.tooling.preview)
    //api(libs.androidx.compose.ui.util)

    //debugApi(libs.androidx.compose.ui.tooling)

    implementation(projects.core.designsystem)
    implementation(projects.core.model)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.coil.kt.compose)
    implementation(libs.lottie.compose)
}
