package com.clarkelamothe.alkemychallengemovie.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clarkelamothe.alkemychallengemovie.view.MainActivity
import com.clarkelamothe.alkemychallengemovie.R
import com.clarkelamothe.alkemychallengemovie.model.Result

class MovieAdapter(private val movies: List<Result>, val listener: MainActivity): RecyclerView.Adapter<MovieViewHolder>(){

    interface OnClickListener{
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        return MovieViewHolder(layoutInflater.inflate(R.layout.card_movie, parent, false), listener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem: Result = movies[position]
        holder.bind(movieItem)
    }


    override fun getItemCount(): Int = movies.size



}