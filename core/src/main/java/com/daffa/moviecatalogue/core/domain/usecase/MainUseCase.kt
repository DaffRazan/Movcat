package com.daffa.moviecatalogue.core.domain.usecase

import com.daffa.moviecatalogue.core.data.source.Resource
import com.daffa.moviecatalogue.core.domain.model.Movie
import com.daffa.moviecatalogue.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    // get lists movies and tv shows
    fun getMovies() : Flow<Resource<List<Movie>>>
    fun getTvShows(): Flow<Resource<List<TvShow>>>

    // get movie and tv show detail
    fun getMovieById(id: Int): Flow<Resource<Movie>>
    fun getTvShowById(id: Int): Flow<Resource<TvShow>>

    // get com.daffa.moviecatalogue.favorite movies and tv shows
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun getFavoriteTvShows(): Flow<List<TvShow>>

    // set com.daffa.moviecatalogue.favorite movies and tv shows
    fun setFavoriteMovies(movie: Movie, state: Boolean)
    fun setFavoriteTvShow(tvShow: TvShow, state: Boolean)
}