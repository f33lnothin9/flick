package ru.resodostudios.flick.feature.settings

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
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
                        content = {
                            settings(
                                settings = settingsUiState.settings,
                                supportDynamicColor = supportDynamicColor,
                                onChangeDynamicColorPreference = onChangeDynamicColorPreference,
                                onChangeDarkThemeConfig = onChangeDarkThemeConfig
                            )
                        }
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun LazyListScope.settings(
    settings: UserEditableSettings,
    supportDynamicColor: Boolean,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit
) {
    item {
        Text(
            text = stringResource(R.string.theme),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 1,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )
    }

    item {
        val options = listOf(
            stringResource(R.string.system),
            stringResource(R.string.light),
            stringResource(R.string.dark)
        )
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
        ) {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                    onClick = { onChangeDarkThemeConfig(DarkThemeConfig.entries[index]) },
                    selected = settings.darkThemeConfig == DarkThemeConfig.entries[index]
                ) {
                    Text(label)
                }
            }
        }
    }
    item {
        AnimatedVisibility(visible = supportDynamicColor) {
            ListItem(
                headlineContent = {
                    Text(
                        text = stringResource(R.string.dynamic_color),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                trailingContent = {
                    Switch(
                        checked = settings.useDynamicColor,
                        onCheckedChange = { onChangeDynamicColorPreference(it) }
                    )
                },
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }

    item {
        Text(
            text = stringResource(R.string.about),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 1,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )
    }

    item {
        val context = LocalContext.current
        ListItem(
            headlineContent = { Text(text = stringResource(R.string.open_source_licenses)) },
            supportingContent = { Text(text = stringResource(R.string.open_source_licenses_details)) },
            modifier = Modifier.clickable {
                context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            }
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