package com.daffa.moviecatalogue.core.data.source.remote.network

import com.daffa.moviecatalogue.BuildConfig
import com.daffa.moviecatalogue.core.data.source.remote.response.DetailMovieResponse
import com.daffa.moviecatalogue.core.data.source.remote.response.DetailTvShowResponse
import com.daffa.moviecatalogue.core.data.source.remote.response.MovieResponse
import com.daffa.moviecatalogue.core.data.source.remote.response.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("discover/movie?api_key=${BuildConfig.API_KEY}")
    fun getMovies(): Call<MovieResponse>?

    @GET("discover/tv?api_key=${BuildConfig.API_KEY}")
    fun getTvShows(): Call<TvShowResponse>?

    @GET("movie/{id}?api_key=${BuildConfig.API_KEY}")
    fun getMovieById(
        @Path("id") id: Int,
    ): Call<DetailMovieResponse>

    @GET("tv/{id}?api_key=${BuildConfig.API_KEY}")
    fun getTvShowById(
        @Path("id") id: Int,
    ): Call<DetailTvShowResponse>
}