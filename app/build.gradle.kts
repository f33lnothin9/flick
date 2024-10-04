import ru.resodostudio.flick.FlickBuildType

plugins {
    alias(libs.plugins.flick.android.application)
    alias(libs.plugins.flick.android.application.compose)
    alias(libs.plugins.flick.android.application.firebase)
    alias(libs.plugins.flick.hilt)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.android.gms.oss-licenses-plugin")
}

android {
    defaultConfig {
        applicationId = "ru.resodostudio.flick"
        versionCode = 1
        versionName = "2.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = FlickBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            applicationIdSuffix = FlickBuildType.RELEASE.applicationIdSuffix
            signingConfig = signingConfigs.named("debug").get()
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "ru.resodostudio.flick"
}

dependencies {
    implementation(projects.feature.home)
    implementation(projects.feature.movies)
    implementation(projects.feature.people)
    //implementation(projects.feature.favorites)
    implementation(projects.feature.movie)
    implementation(projects.feature.person)
    //implementation(projects.feature.search)
    implementation(projects.feature.settings)

    implementation(projects.core.common)
    implementation(projects.core.data)
    //implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
    implementation(projects.core.ui)

    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)

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