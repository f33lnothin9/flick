plugins {
    alias(libs.plugins.flick.jvm.library)
    alias(libs.plugins.flick.hilt)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}