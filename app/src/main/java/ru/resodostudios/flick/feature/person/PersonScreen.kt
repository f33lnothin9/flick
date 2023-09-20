package ru.resodostudios.flick.feature.person

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.resodostudios.flick.R
import ru.resodostudios.flick.core.designsystem.component.FlickAsyncImage
import ru.resodostudios.flick.core.designsystem.icon.FlickIcons
import ru.resodostudios.flick.core.designsystem.theme.Typography
import ru.resodostudios.flick.core.model.data.Person
import ru.resodostudios.flick.core.model.data.PersonExtended
import ru.resodostudios.flick.core.ui.EmptyState
import ru.resodostudios.flick.core.ui.LoadingState

@Composable
internal fun PersonRoute(
    personViewModel: PersonViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {

    val personState by personViewModel.personUiState.collectAsStateWithLifecycle()

    PersonScreen(
        personState = personState,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PersonScreen(
    personState: PersonUiState,
    onBackClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    when (personState) {
        PersonUiState.Loading -> LoadingState()
        is PersonUiState.Success -> {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopAppBar(
                        title = { },
                        navigationIcon = {
                            IconButton(
                                onClick = onBackClick,
                                content = {
                                    Icon(
                                        imageVector = FlickIcons.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            )
                        },
                        scrollBehavior = scrollBehavior
                    )
                },
                contentWindowInsets = WindowInsets.waterfall,
                content = {
                    LazyColumn(
                        contentPadding = it,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        content = {
                            item {
                                PersonHeader(person = personState.data.person)
                            }
                            item {
                                PersonBody(
                                    personExtended = personState.data
                                )
                            }
                        }
                    )
                }
            )
        }

        is PersonUiState.Error -> EmptyState(
            message = personState.errorMessage,
            animationId = R.raw.anim_error_2
        )
    }
}

@Composable
private fun PersonHeader(person: Person) {
    Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        FlickAsyncImage(
            url = person.image.original,
            contentDescription = null,
            modifier = Modifier
                .size(width = 125.dp, height = 176.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = person.name,
                style = Typography.headlineSmall,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )

            Column {
                Text(
                    text = person.country.name + ", ${person.country.code} ",
                    style = Typography.labelLarge,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = person.birthday + " - " + person.deathday,
                    style = Typography.labelLarge,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = person.gender,
                    style = Typography.labelLarge,
                    textAlign = TextAlign.Start
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    HorizontalDivider()
}

@Composable
private fun PersonBody(personExtended: PersonExtended) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {

        }


    }
}