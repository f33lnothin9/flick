package ru.resodostudio.flick.core.network.model

import kotlinx.serialization.Serializable
import ru.resodostudio.flick.core.model.data.ImageExtended
import ru.resodostudio.flick.core.model.data.Resolution
import ru.resodostudio.flick.core.model.data.Resolutions

@Serializable
data class NetworkImageExtended(
    val id: Int = 0,
    val main: Boolean = false,
    val resolutions: NetworkResolutions,
    val type: String = ""
)

fun NetworkImageExtended.asExternalModel() = ImageExtended(
    id = id,
    main = main,
    type = type,
    resolutions = Resolutions(
        medium = Resolution(
            url = resolutions.medium.url,
            height = resolutions.medium.height,
            width = resolutions.medium.width
        ),
        original = Resolution(
            url = resolutions.original.url,
            height = resolutions.original.height,
            width = resolutions.original.width
        )
    )
)