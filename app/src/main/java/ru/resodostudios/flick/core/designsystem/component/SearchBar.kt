package ru.resodostudios.flick.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.zIndex
import ru.resodostudios.flick.core.designsystem.icon.FlickIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    @StringRes titleRes: Int,
    onSearch: (String) -> Unit,
    onBackClick: () -> Unit,
    content: @Composable (ColumnScope.() -> Unit)
) {

    var query by rememberSaveable { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    Box(
        Modifier
            .semantics { isTraversalGroup = true }
            .zIndex(1f)
            .fillMaxWidth()
    ) {
        SearchBar(
            modifier = Modifier
                .focusRequester(focusRequester)
                .align(Alignment.TopCenter),
            query = query,
            onQueryChange = {
                query = it
                onSearch(it)
            },
            onSearch = { focusManager.clearFocus() },
            active = true,
            onActiveChange = { },
            placeholder = { Text(text = stringResource(titleRes)) },
            leadingIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(FlickIcons.ArrowBack, contentDescription = null)
                }
            },
            trailingIcon = {
                if (query.isNotBlank()) {
                    IconButton(
                        onClick = { query = "" }
                    ) {
                        Icon(Icons.Default.Close, contentDescription = null)
                    }
                }
            },
            windowInsets = WindowInsets.statusBars,
            content = content
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}