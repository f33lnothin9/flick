package ru.resodostudio.core.data.repository.offline

import kotlinx.coroutines.flow.Flow
import ru.resodostudio.core.data.repository.UserDataRepository
import ru.resodostudio.datastore.FlickPreferencesDataSource
import ru.resodostudio.flick.core.model.data.DarkThemeConfig
import ru.resodostudio.flick.core.model.data.UserData
import javax.inject.Inject

class OfflineUserDataRepository @Inject constructor(
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