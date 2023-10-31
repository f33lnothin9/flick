package ru.resodostudios.flick

/**
 * This is shared between :app and :benchmarks module to provide configurations type safety.
 */
enum class FlickBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE,
    BENCHMARK(".benchmark")
}