package ru.resodostudios.flick.feature.people

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.designsystem.component.FlickAsyncImage
import ru.resodostudios.flick.core.model.data.Person
import ru.resodostudios.flick.core.ui.EmptyState
import ru.resodostudios.flick.core.ui.LoadingState
import ru.resodostudios.flick.feature.people.PeopleUiState.Error
import ru.resodostudios.flick.feature.people.PeopleUiState.Loading
import ru.resodostudios.flick.feature.people.PeopleUiState.Success

@Composable
internal fun PeopleRoute(
    onPersonClick: (Int) -> Unit,
    viewModel: PeopleViewModel = hiltViewModel()
) {
    val peopleState by viewModel.peopleUiState.collectAsStateWithLifecycle()
    PeopleScreen(
        peopleState = peopleState,
        onPersonClick = onPersonClick
    )
}

@Composable
internal fun PeopleScreen(
    peopleState: PeopleUiState,
    onPersonClick: (Int) -> Unit
) {
    when (peopleState) {
        Loading -> LoadingState()
        is Success -> if (peopleState.people.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(300.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                people(
                    people = peopleState.people,
                    onPersonClick = onPersonClick
                )
            }
        }

        is Error -> EmptyState(
            message = peopleState.errorMessage,
            animationId = R.raw.anim_error_1
        )
    }
}

private fun LazyGridScope.people(
    people: List<Person>,
    onPersonClick: (Int) -> Unit
) {
    items(people) { person ->
        ListItem(
            headlineContent = { Text(text = person.name) },
            supportingContent = {
                Text(text = person.country.name.ifBlank { stringResource(id = R.string.country_empty) })
            },
            leadingContent = {
                Box {
                    FlickAsyncImage(
                        url = person.image.medium,
                        contentDescription = "Person image",
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .size(56.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            },
            modifier = Modifier.clickable { onPersonClick(person.id) }
        )
    }
}