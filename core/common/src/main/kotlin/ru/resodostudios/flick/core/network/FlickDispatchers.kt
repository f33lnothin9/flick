package ru.resodostudios.flick.core.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val flickDispatcher: FlickDispatchers)

enum class FlickDispatchers {
    Default,
    IO,
}