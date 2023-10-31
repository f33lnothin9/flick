plugins {
    alias(libs.plugins.flick.android.library)
    alias(libs.plugins.flick.android.hilt)
    alias(libs.plugins.flick.android.room)
}

android {
    namespace = "ru.resodostudios.flick.core.database"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.kotlinx.coroutines.android)
}
