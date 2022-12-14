package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val movieRepository: MovieRepository,
    private val favoritesScreenMapper: FavoritesMapper,
    private val bgDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _favoritesViewState = MutableStateFlow(FavoritesViewState(emptyList()))
    val favoritesViewState: StateFlow<FavoritesViewState> = _favoritesViewState.asStateFlow()

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch {
            movieRepository.favoriteMovies().collect {
                _favoritesViewState.value = favoritesScreenMapper.toFavoritesViewState(it)
            }
        }
    }

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch(bgDispatcher) {
            movieRepository.toggleFavorite(movieId)
        }
    }
}
