package ru.resodostudio.flick

enum class FlickBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE,
}