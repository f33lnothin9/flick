plugins {
    alias(libs.plugins.flick.android.feature)
    alias(libs.plugins.flick.android.library.compose)
}

android {
    namespace = "ru.resodostudio.flick.feature.movie"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
}