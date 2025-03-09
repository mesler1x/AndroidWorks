package ru.mesler.androidworks.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.mesler.androidworks.domain.model.Movie
import ru.mesler.androidworks.domain.model.Rating
import ru.mesler.androidworks.theme.Spacing
import ru.mesler.androidworks.theme.Typography
import ru.mesler.androidworks.viewModel.DetailsViewModel

@Composable
fun DetailsScreen(navigation: NavHostController, movieId: Int) {
    val viewModel = koinViewModel<DetailsViewModel> { parametersOf(navigation, movieId) }

    MovieScreenContent(
        viewModel.mutableState.movie,
        onBackPressed = { viewModel.back() }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieScreenContent(
    movie: Movie?,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = movie?.name.orEmpty(),
                        style = Typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onBackPressed.invoke() }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        movie ?: run {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "По запросу нет результатов")
            }
            return@Scaffold
        }
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row {
                AsyncImage(
                    model = movie.poster.url,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .height(300.dp)
                        .width(200.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Описание: ${movie.description}",
                    style = Typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            RatingDisplay(movie.rating)

            Text(
                text = "Жанр:",
                style = Typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            LazyColumn {
                items(movie.genres) { genre ->
                    Text(
                        text = genre.name,
                        style = Typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.medium))


            Text(
                text = "Страны выхода:",
                style = Typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            LazyColumn {
                items(movie.countries) { country ->
                    Text(
                        text = country.name,
                        style = Typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.medium))

            Text(text = "Съемочная команда:", style = Typography.bodyLarge)
            LazyRow(
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                items(movie.persons) { person ->
                    Row(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = person.photo,
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                        )

                        Spacer(modifier = Modifier.width(Spacing.small))
                        Column {
                            Text(text = person.name, style = Typography.bodyLarge)
                            Text(
                                text = person.profession,
                                style = Typography.bodyLarge,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RatingDisplay(rating: Rating) {
    Row(
        modifier = Modifier
            .padding(vertical = 12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Kp: ${rating.kp}",
            style = Typography.bodyLarge.copy(color = Color.Gray),
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Text(
            text = "IMDB: ${rating.imdb}",
            style = Typography.bodyLarge.copy(color = Color.Gray),
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Text(
            text = "Film Critics: ${rating.filmCritics}",
            style = Typography.bodyLarge.copy(color = Color.Gray),
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
}