package ru.resodostudio.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.resodostudio.flick.core.model.data.DarkThemeConfig
import ru.resodostudio.flick.core.model.data.UserData

interface UserDataRepository {

    val userData: Flow<UserData>

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)
}