package agency.five.codebase.android.movieapp.navigation

import agency.five.codebase.android.movieapp.R

const val HOME_ROUTE = "Home"
const val FAVORITES_ROUTE = "Favorites"
const val MOVIE_DETAILS_ROUTE = "MovieDetails"
const val MOVIE_ID_KEY = "movieId"
const val MOVIE_DETAILS_ROUTE_WITH_PARAMS = "$MOVIE_DETAILS_ROUTE/{$MOVIE_ID_KEY}"

sealed class NavigationItem(
    override val route: String,
    val selectedIconId: Int,
    val unselectedIconId: Int,
    val labelId: Int,
) : MovieAppDestination(route) {
    object HomeDestination : NavigationItem(
        route = HOME_ROUTE,
        selectedIconId = R.drawable.ic_home_filled,
        unselectedIconId = R.drawable.ic_home_outlined,
        labelId = R.string.home,
    )

    object FavoritesDestination : NavigationItem(
        route = FAVORITES_ROUTE,
        selectedIconId = R.drawable.ic_favorite_filled,
        unselectedIconId = R.drawable.ic_favorite,
        labelId = R.string.favorites,
    )
}
