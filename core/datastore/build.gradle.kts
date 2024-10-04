plugins {
    alias(libs.plugins.flick.android.library)
    alias(libs.plugins.flick.hilt)
}

android {
    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }
    namespace = "ru.resodostudio.flick.core.datastore"
    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    api(projects.core.datastoreProto)
    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.protobuf.kotlin.lite)
}