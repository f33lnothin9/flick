import java.util.Properties

plugins {
    alias(libs.plugins.flick.android.library)
    alias(libs.plugins.flick.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        proguardFiles("consumer-rules.pro")

        val localPropertiesFile = rootProject.file("local.properties")
        val localProperties = Properties()
        localProperties.load(localPropertiesFile.inputStream())

        val backendUrl = localProperties.getProperty("BACKEND_URL")
        val apiKey = localProperties.getProperty("API_KEY")

        buildConfigField("String", "BACKEND_URL", "\"$backendUrl\"")
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
    }
    namespace = "ru.resodostudios.flick.core.network"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)

    implementation(libs.coil.kt.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.retrofit.kotlin.serialization)
}
