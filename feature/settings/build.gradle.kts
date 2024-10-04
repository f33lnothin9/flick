plugins {
    alias(libs.plugins.flick.android.feature)
    alias(libs.plugins.flick.android.library.compose)
}

android {
    namespace = "ru.resodostudio.flick.feature.settings"
}

dependencies {
    implementation(projects.core.data)

    implementation(libs.androidx.appcompat)
    implementation(libs.google.oss.licenses) {
        exclude(group = "androidx.appcompat")
    }
}