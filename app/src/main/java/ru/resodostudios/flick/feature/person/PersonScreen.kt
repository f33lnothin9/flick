package ru.resodostudios.flick.feature.person

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs

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
                                PersonHeader(
                                    person = personState.data.person
                                )
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

                PersonDate(
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
private fun PersonDate(birthday: String, deathday: String) {
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
private fun PersonBody(personExtended: PersonExtended) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var castSize by rememberSaveable { mutableIntStateOf(288) }

        if (personExtended.castCredits.size < 4 && personExtended.castCredits.isNotEmpty()) castSize =
            72 * personExtended.castCredits.size

        if (personExtended.castCredits.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp, top = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.cast_credits),
                    style = Typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(start = 16.dp)
                )

                LazyHorizontalGrid(
                    rows = GridCells.Adaptive(72.dp),
                    modifier = Modifier.height(castSize.dp)
                ) {
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
                            supportingContent = { Text(text = castCredit.embedded.character.name) }
                        )
                    }
                }
            }
        }
    }
}


fun formatDate(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val rawDate = inputFormat.parse(date)

    return outputFormat.format(rawDate!!)
}