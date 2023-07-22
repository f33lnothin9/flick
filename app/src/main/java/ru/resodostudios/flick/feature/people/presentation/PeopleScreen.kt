package ru.resodostudios.flick.feature.people.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import ru.resodostudios.flick.core.presentation.components.RetrySection

@Composable
fun PeopleScreen(
    state: PeopleUiState,
    onRetry: () -> Unit,
    drawerState: DrawerState
) {

    Surface(Modifier.fillMaxSize()) {
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
                        supportingContent = { Text(text = it.country?.name.toString()) },
                        leadingContent = {
                            Box {
                                SubcomposeAsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(it.image?.medium)
                                        .crossfade(400)
                                        .size(256)
                                        .transformations(
                                            CircleCropTransformation()
                                        )
                                        .build(),
                                    contentDescription = "Image",
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    filterQuality = FilterQuality.Low,
                                    loading = { CircularProgressIndicator() }
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
}