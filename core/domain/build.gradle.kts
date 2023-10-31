plugins {
    alias(libs.plugins.flick.android.library)
    alias(libs.plugins.flick.android.hilt)
}

android {
    namespace = "ru.resodostudios.flick.core.domain"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(libs.kotlinx.coroutines.android)
}