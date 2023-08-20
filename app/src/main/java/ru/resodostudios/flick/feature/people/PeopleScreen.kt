package ru.resodostudios.flick.feature.people

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.designsystem.component.RetrySection

@Composable
internal fun PeopleRoute(
    viewModel: PeopleViewModel = hiltViewModel()
) {
    val peopleState by viewModel.state.collectAsStateWithLifecycle()

    PeopleScreen(
        state = peopleState,
        onRetry = viewModel::getPeople
    )
}

@Composable
internal fun PeopleScreen(
    state: PeopleUiState,
    onRetry: () -> Unit
) {
    AnimatedVisibility(
        visible = !state.isLoading,
        enter = fadeIn()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(state.people) {
                ListItem(
                    headlineContent = { Text(text = it.name.toString()) },
                    supportingContent = { Text(text = it.country?.name ?: "Unknown Country") },
                    leadingContent = {
                        Box {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(it.image?.medium)
                                    .crossfade(400)
                                    .size(256)
                                    .error(if (isSystemInDarkTheme()) R.drawable.ic_outlined_face_white else R.drawable.ic_outlined_face)
                                    .transformations(CircleCropTransformation())
                                    .build(),
                                contentDescription = "Image",
                                modifier = Modifier.size(56.dp),
                                filterQuality = FilterQuality.Low
                            )
                        }
                    }
                )
            }
        }
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    if (state.isError) RetrySection(onClick = onRetry)
}