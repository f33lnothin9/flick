package ru.resodostudios.flick.feature.person

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import ru.resodostudios.flick.core.common.formatDate
import ru.resodostudios.flick.core.designsystem.component.FlickAsyncImage
import ru.resodostudios.flick.core.designsystem.component.NoTitleTopAppBar
import ru.resodostudios.flick.core.designsystem.theme.Typography
import ru.resodostudios.flick.core.model.data.FavoritePerson
import ru.resodostudios.flick.core.model.data.Person
import ru.resodostudios.flick.core.model.data.PersonExtended
import ru.resodostudios.flick.core.ui.BodySection
import ru.resodostudios.flick.core.ui.EmptyState
import ru.resodostudios.flick.core.ui.LoadingState
import ru.resodostudios.flick.feature.favorites.FavoriteEvent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs

@Composable
internal fun PersonRoute(
    personViewModel: PersonViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    val personState by personViewModel.personUiState.collectAsStateWithLifecycle()
    PersonScreen(
        personState = personState,
        onBackClick = onBackClick,
        onMovieClick = onMovieClick,
        onEvent = personViewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PersonScreen(
    personState: PersonUiState,
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onEvent: (FavoriteEvent) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    when (personState) {
        PersonUiState.Loading -> LoadingState()
        is PersonUiState.Success -> {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    NoTitleTopAppBar(
                        scrollBehavior = scrollBehavior,
                        onNavIconClick = onBackClick,
                        actions = {
                            if (personState.data.isFavorite) {
                                IconButton(
                                    onClick = {
                                        onEvent(
                                            FavoriteEvent.DeletePerson(
                                                FavoritePerson(
                                                    id = personState.data.person.id,
                                                    name = personState.data.person.name,
                                                    image = personState.data.person.image.medium
                                                )
                                            )
                                        )
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Favorite,
                                        contentDescription = "Favorite"
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = { onEvent(FavoriteEvent.AddPerson(personState.data.person)) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Favorite"
                                    )
                                }
                            }
                        }
                    )
                },
                contentWindowInsets = WindowInsets.waterfall,
                content = {
                    LazyColumn(
                        contentPadding = it,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        content = {
                            item {
                                PersonHeader(
                                    person = personState.data.person
                                )
                            }
                            item {
                                PersonBody(
                                    personExtended = personState.data,
                                    onMovieClick = onMovieClick
                                )
                            }
                            item { Spacer(modifier = Modifier.height(50.dp)) }
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

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (person.country.name.isNotBlank()) {
                    Text(
                        text = person.country.name + ", ${person.country.code}",
                        style = Typography.labelLarge,
                        textAlign = TextAlign.Start
                    )
                }

                PersonHeaderDate(
                    birthday = person.birthday,
                    deathday = person.deathday
                )

                if (person.gender.isNotBlank()) {
                    Text(
                        text = person.gender,
                        style = Typography.labelLarge,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    HorizontalDivider()
}

@Composable
private fun PersonHeaderDate(birthday: String, deathday: String) {
    if (birthday.isNotBlank()) {
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val birthDate = dateFormat.parse(birthday)
        val currentDate =
            if (deathday.isNotBlank()) dateFormat.parse(deathday) else inputFormat.parse(Date().toString())

        val ageInMillis = abs(currentDate!!.time - birthDate!!.time)

        val ageCalendar = Calendar.getInstance()
        ageCalendar.timeInMillis = ageInMillis

        val age = ageCalendar.get(Calendar.YEAR) - 1970

        Text(
            text = formatDate(birthday) + (if (deathday.isNotBlank()) " - " + formatDate(deathday) else "") + ", $age years",
            style = Typography.labelLarge,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun PersonBody(
    personExtended: PersonExtended,
    onMovieClick: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (personExtended.castCredits.isNotEmpty()) {
            BodySection(
                title = R.string.cast_credits,
                itemsSize = personExtended.castCredits.size,
                content = {
                    items(personExtended.castCredits) { castCredit ->
                        ListItem(
                            headlineContent = { Text(text = castCredit.embedded.movie.name) },
                            leadingContent = {
                                Box {
                                    FlickAsyncImage(
                                        url = castCredit.embedded.movie.image.medium,
                                        contentDescription = "Movie image",
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .size(56.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            },
                            supportingContent = { Text(text = castCredit.embedded.character.name) },
                            modifier = Modifier.clickable { onMovieClick(castCredit.embedded.movie.id) }
                        )
                    }
                }
            )
        }
        if (personExtended.crewCredits.isNotEmpty()) {
            BodySection(
                title = R.string.crew_credits,
                itemsSize = personExtended.crewCredits.size,
                content = {
                    items(personExtended.crewCredits) { crewCredit ->
                        ListItem(
                            headlineContent = { Text(text = crewCredit.embedded.movie.name) },
                            leadingContent = {
                                Box {
                                    FlickAsyncImage(
                                        url = crewCredit.embedded.movie.image.medium,
                                        contentDescription = "Movie image",
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .size(56.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            },
                            supportingContent = { Text(text = crewCredit.type) },
                            modifier = Modifier.clickable { onMovieClick(crewCredit.embedded.movie.id) }
                        )
                    }
                }
            )
        }
    }
}