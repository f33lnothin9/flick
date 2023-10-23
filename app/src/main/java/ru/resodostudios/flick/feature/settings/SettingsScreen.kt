package ru.resodostudios.flick.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.designsystem.component.FlickLargeTopAppBar
import ru.resodostudios.flick.core.designsystem.icon.FlickIcons
import ru.resodostudios.flick.core.designsystem.theme.supportsDynamicTheming
import ru.resodostudios.flick.core.model.data.DarkThemeConfig
import ru.resodostudios.flick.core.ui.LoadingState

@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settingsUiState by viewModel.settingsUiState.collectAsStateWithLifecycle()
    SettingsScreen(
        onBackClick = onBackClick,
        settingsUiState = settingsUiState,
        onChangeDynamicColorPreference = viewModel::updateDynamicColorPreference,
        onChangeDarkThemeConfig = viewModel::updateDarkThemeConfig,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    settingsUiState: SettingsUiState,
    supportDynamicColor: Boolean = supportsDynamicTheming(),
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    when (settingsUiState) {
        SettingsUiState.Loading -> LoadingState()
        is SettingsUiState.Success -> {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    FlickLargeTopAppBar(
                        titleRes = R.string.settings,
                        scrollBehavior = scrollBehavior,
                        navigationIcon = FlickIcons.ArrowBack,
                        navigationIconContentDescription = stringResource(R.string.large_top_app_bar_navigation_icon_description),
                        onNavigationClick = onBackClick
                    )
                },
                content = { innerPadding ->
                    LazyColumn(
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                        contentPadding = innerPadding,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        content = {
                            settings()
                        }
                    )
                }
            )
        }
    }
}

private fun LazyListScope.settings(

) {
    item {
        Text(
            text = stringResource(R.string.theme),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 1,
            modifier = Modifier.padding(start = 16.dp)
        )
    }

    item {
        Text(
            text = stringResource(R.string.about),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 1,
            modifier = Modifier.padding(start = 16.dp)
        )
    }

    item {
        val context = LocalContext.current
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        ListItem(
            headlineContent = { Text(text = stringResource(R.string.version)) },
            supportingContent = { Text(text = packageInfo.versionName) }
        )
    }
}