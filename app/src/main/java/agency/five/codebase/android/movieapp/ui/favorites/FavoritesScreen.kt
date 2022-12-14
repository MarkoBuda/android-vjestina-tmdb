package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapper
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun FavoritesRoute(
    onNavigateToMovieDetails: (Int) -> Unit,
    viewModel: FavoritesViewModel
) {
    val viewState: FavoritesViewState by viewModel.favoritesViewState.collectAsState()
    FavoritesScreen(
        viewState = viewState,
        onCardClick = onNavigateToMovieDetails,
        onLikeButtonClick = { movieId -> viewModel.toggleFavorite(movieId) }
    )
}

@Composable
fun FavoritesScreen(
    viewState: FavoritesViewState,
    onCardClick: (Int) -> Unit,
    onLikeButtonClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = dimensionResource(id = R.dimen.favorites_movie_card_width)),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        contentPadding = PaddingValues(MaterialTheme.spacing.medium),
        modifier = modifier
    ) {
        title {
            Text(
                text = stringResource(id = R.string.favorites_screen_header),
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(top = MaterialTheme.spacing.medium)
            )
        }
        items(
            items = viewState.favoritesMovies,
            key = { favoriteMovie -> favoriteMovie.id }
        ) { favoriteMovie ->
            MovieCard(
                movieCardViewState = favoriteMovie.movieCardViewState,
                onCardClick = { onCardClick(favoriteMovie.id) },
                onLikeButtonClick = { onLikeButtonClick(favoriteMovie.id) },
                modifier.heightIn(max = dimensionResource(id = R.dimen.movie_card_height))
            )
        }
    }
}

fun LazyGridScope.title(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}

@Preview
@Composable
fun FavoritesScreenPreview() {
    val favoritesMapper: FavoritesMapper = FavoritesMapperImpl()
    val favoritesViewState = favoritesMapper.toFavoritesViewState(MoviesMock.getMoviesList())
    MovieAppTheme {
        FavoritesScreen(
            viewState = favoritesViewState,
            onCardClick = {},
            onLikeButtonClick = {}
        )
    }
}
