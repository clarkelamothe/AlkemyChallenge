package com.clarkelamothe.alkemychallengemovie.api

import com.clarkelamothe.alkemychallengemovie.model.Movie
import com.clarkelamothe.alkemychallengemovie.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("movie/popular?")
    suspend fun fetchPopularMovies(@Query("api_key") api_key: String): Response<MovieResponse>


    @GET("{movie_id}")
    suspend fun fetchMovieById(@Path("movie_id") id: String, @Query("api_key") api_key: String): Response<Movie>

    @GET("search/movie?")
    suspend fun fetchMovieByName( @Query("query") query:String, @Query("api_key") api_key: String): Response<MovieResponse>

}