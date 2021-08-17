package com.clarkelamothe.alkemychallengemovie.viewmodel

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.clarkelamothe.alkemychallengemovie.api.Tmdb
import com.clarkelamothe.alkemychallengemovie.databinding.CardMovieBinding
import com.clarkelamothe.alkemychallengemovie.model.Result
import com.squareup.picasso.Picasso

class MovieViewHolder(
    view: View,
    val listener: MovieAdapter.OnClickListener
                      ): RecyclerView.ViewHolder(view) {

    val binding = CardMovieBinding.bind(view)

    fun bind(movie: Result){
        Picasso.get().load("${Tmdb.BASE_IMG_URL}${movie.posterPath}").into(binding.ivMovieImage)
        binding.tvMovieName.text = movie.originalTitle
//        binding.tvMovieOverview.text = movie.overview
    }


    init {
        view.setOnClickListener{
            listener.onItemClick(adapterPosition)
        }
    }

}