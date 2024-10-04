plugins {
    alias(libs.plugins.flick.android.feature)
    alias(libs.plugins.flick.android.library.compose)
}

android {
    namespace = "ru.resodostudio.flick.feature.people"
}

dependencies {
    implementation(projects.core.data)
}