plugins {
    alias(libs.plugins.flick.android.library)
    alias(libs.plugins.flick.android.hilt)
}

android {
    namespace = "ru.resodostudios.flick.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}