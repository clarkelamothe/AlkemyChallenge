package com.clarkelamothe.alkemychallengemovie.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

    fun getRetroFit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Tmdb.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}