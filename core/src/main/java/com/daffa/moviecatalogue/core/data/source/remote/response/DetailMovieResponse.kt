package com.daffa.moviecatalogue.core.data.source.remote.response

import androidx.annotation.Keep
import com.daffa.moviecatalogue.core.data.source.remote.response.model.Genre

@Keep
data class DetailMovieResponse(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val budget: Int = 0,
    val genres: List<Genre> = listOf(),
    val homepage: String = "",
    val id: Int,
    val imdb_id: String = "",
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val release_date: String = "",
    val revenue: Int = 0,
    val runtime: Int = 0,
    val status: String = "",
    val tagline: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Float = 0.0f,
    val vote_count: Int = 0
)