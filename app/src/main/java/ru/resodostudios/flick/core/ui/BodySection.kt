package ru.resodostudios.flick.core.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.resodostudios.flick.core.designsystem.theme.Typography

@Composable
fun BodySection(
    @StringRes title: Int,
    itemsSize: Int,
    content: LazyGridScope.() -> Unit
) {
    var sectionSize by rememberSaveable { mutableIntStateOf(288) }

    if (itemsSize < 4) sectionSize =
        72 * itemsSize

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(bottom = 16.dp, top = 8.dp)
    ) {
        Text(
            text = stringResource(title),
            style = Typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(start = 16.dp)
        )

        LazyHorizontalGrid(
            rows = GridCells.Adaptive(72.dp),
            modifier = Modifier.height(sectionSize.dp),
            content = content
        )
    }
}