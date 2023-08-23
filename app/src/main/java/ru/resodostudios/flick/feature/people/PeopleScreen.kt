package ru.resodostudios.flick.feature.people

import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.model.data.Person
import ru.resodostudios.flick.core.ui.ErrorState
import ru.resodostudios.flick.core.ui.LoadingState
import ru.resodostudios.flick.feature.people.PeopleUiState.Error
import ru.resodostudios.flick.feature.people.PeopleUiState.Loading
import ru.resodostudios.flick.feature.people.PeopleUiState.Success

@Composable
internal fun PeopleRoute(
    viewModel: PeopleViewModel = hiltViewModel()
) {
    val peopleState by viewModel.peopleUiState.collectAsStateWithLifecycle()
    PeopleScreen(
        peopleState = peopleState
    )
}

@Composable
internal fun PeopleScreen(
    peopleState: PeopleUiState
) {

    when (peopleState) {
        Loading -> LoadingState()
        is Success -> if (peopleState.people.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(300.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    people(
                        people = peopleState.people
                    )
                }
            }
        }

        is Error -> ErrorState(
            errorMessage = peopleState.errorMessage,
            animationId = R.raw.anim_error_1
        )
    }
}

private fun LazyGridScope.people(
    people: List<Person>
) {
    items(people) { person ->
        ListItem(
            headlineContent = { Text(text = person.name) },
            supportingContent = { Text(text = person.country.name) },
            leadingContent = {
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(person.image.medium)
                            .crossfade(400)
                            .size(256)
                            .error(if (isSystemInDarkTheme()) R.drawable.ic_outlined_face_white else R.drawable.ic_outlined_face)
                            .build(),
                        contentDescription = "Image",
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .size(56.dp),
                        filterQuality = FilterQuality.Low,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        )
    }
}