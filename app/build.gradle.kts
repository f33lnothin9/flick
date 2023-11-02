plugins {
    alias(libs.plugins.flick.android.application)
    alias(libs.plugins.flick.android.application.compose)
    alias(libs.plugins.flick.android.application.firebase)
    alias(libs.plugins.flick.android.hilt)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.android.gms.oss-licenses-plugin")
}

android {
    defaultConfig {
        applicationId = "ru.resodostudios.flick"
        versionCode = 7
        versionName = "1.3.0-alpha"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "ru.resodostudios.flick"
    buildToolsVersion = "34.0.0"
}

dependencies {
    implementation(projects.feature.movies)
    implementation(projects.feature.people)
    implementation(projects.feature.favorites)
    implementation(projects.feature.movie)
    implementation(projects.feature.person)
    implementation(projects.feature.search)
    implementation(projects.feature.settings)

    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
    implementation(projects.core.ui)

    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // UI Tests
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.test.manifest)

    implementation(libs.androidx.compose.material3.windowSizeClass)

    // Integration with activities
    implementation(libs.androidx.activity.compose)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.hilt.navigation.compose)

    // Coil
    implementation(libs.coil.kt.compose)

    // Splash Screen
    implementation(libs.androidx.core.splashscreen)
}