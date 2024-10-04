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
    api(projects.core.model)
    api(libs.androidx.dataStore)

    implementation(projects.core.common)
}