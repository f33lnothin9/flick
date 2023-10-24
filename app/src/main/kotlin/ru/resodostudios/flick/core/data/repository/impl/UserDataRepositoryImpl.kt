package ru.resodostudios.flick.core.data.repository.impl

import kotlinx.coroutines.flow.Flow
import ru.resodostudios.flick.core.data.repository.UserDataRepository
import ru.resodostudios.flick.core.datastore.FlickPreferencesDataSource
import ru.resodostudios.flick.core.model.data.DarkThemeConfig
import ru.resodostudios.flick.core.model.data.UserData
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val flickPreferencesDataSource: FlickPreferencesDataSource
) : UserDataRepository {

    override val userData: Flow<UserData> =
        flickPreferencesDataSource.userData

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        flickPreferencesDataSource.setDarkThemeConfig(darkThemeConfig)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        flickPreferencesDataSource.setDynamicColorPreference(useDynamicColor)
    }
}