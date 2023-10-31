plugins {
    alias(libs.plugins.flick.android.feature)
    alias(libs.plugins.flick.android.library.compose)
}

android {
    namespace = "ru.resodostudios.flick.feature.settings"
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.google.oss.licenses) {
        exclude(group = "androidx.appcompat")
    }
}