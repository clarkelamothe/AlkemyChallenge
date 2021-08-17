package com.clarkelamothe.alkemychallengemovie.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.clarkelamothe.alkemychallengemovie.api.Tmdb
import com.clarkelamothe.alkemychallengemovie.databinding.ActivityMovieDetailsBinding
import com.squareup.picasso.Picasso



class MovieDetails : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvDetailsTitle.text = this.intent.extras?.get("title").toString()
        binding.tvDetailsOverview.text = this.intent.extras?.get("overview").toString()

        Picasso.get().load("${Tmdb.BASE_IMG_URL}${this.intent.extras?.get("backDrop").toString()}")
            .into(binding.ivDetailsBackDrop)
        Picasso.get().load("${Tmdb.BASE_IMG_URL}${this.intent.extras?.get("imgUrl").toString()}")
            .into(binding.ivDetailsImg)

        binding.tvDetailsLanguage.text = this.intent.extras?.get("language").toString()
        binding.tvDetailsRating.text = this.intent.extras?.get("rating").toString().plus( "/10")
    }
}