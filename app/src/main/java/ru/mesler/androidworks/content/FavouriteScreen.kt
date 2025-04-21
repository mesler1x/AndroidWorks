package ru.mesler.androidworks.content

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.mesler.androidworks.domain.model.MovieShort
import ru.mesler.androidworks.theme.Typography
import ru.mesler.androidworks.viewModel.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(navigation: NavHostController) {
    val viewModel = koinViewModel<FavoritesViewModel> { parametersOf(navigation) }
    val state by viewModel.viewState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Мои фильмы") })
        },
        floatingActionButton = {
            Button(onClick = {viewModel.onUpdateClick()}) {
                Text("Update me")
            }
        }
    ) { innerPadding ->
        LazyColumn(Modifier.padding(innerPadding)) {
            items(state.items) {
                MovieCardShort(it)
            }
        }
    }
}

@Composable
fun MovieCardShort(movie: MovieShort) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = movie.poster.previewUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Column {
                Text(
                    text = movie.name,
                    style = Typography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = "Тип: ${movie.type}",
                    style = Typography.labelSmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}