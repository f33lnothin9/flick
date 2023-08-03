package ru.resodostudios.flick.feature.search.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@ExperimentalMaterial3Api
@Composable
fun SearchBar(
    onSearch: (String) -> Unit,
    onClearSearch: (String) -> Unit,
    onMenuClick: () -> Unit,
    onFilterClick: () -> Unit,
    content: LazyListScope.() -> Unit,
    title: String
) {

    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Box(
        Modifier
            .semantics { isTraversalGroup = true }
            .zIndex(1f)
            .fillMaxWidth()
    ) {
        SearchBar(
            modifier = Modifier.align(Alignment.TopCenter),
            query = text,
            onQueryChange = {
                text = it
                onSearch(it)
            },
            onSearch = { focusManager.clearFocus() },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text(title) },
            leadingIcon = {
                if (active) {
                    IconButton(
                        onClick = {
                            active = false
                            text = ""
                            onClearSearch("")
                        }
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                } else {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }
                }
            },
            trailingIcon = {
                if (text.isNotBlank()) {
                    IconButton(
                        onClick = {
                            text = ""
                            onClearSearch("")
                        }
                    ) {
                        Icon(Icons.Default.Close, contentDescription = null)
                    }
                }
//                if (text.isBlank() && !active) {
//                    IconButton(onClick = onFilterClick) {
//                        Icon(Icons.Outlined.FilterList, contentDescription = null)
//                    }
//                }
            },
            windowInsets = WindowInsets.statusBars
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                content = content
            )
        }
    }
}