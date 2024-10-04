plugins {
    alias(libs.plugins.flick.android.library)
    alias(libs.plugins.flick.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "ru.resodostudio.core.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    api(projects.core.common)
    //api(projects.core.database)
    api(projects.core.datastore)

    implementation(projects.core.network)

    implementation(libs.kotlinx.serialization.json)
}