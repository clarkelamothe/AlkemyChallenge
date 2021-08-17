package com.clarkelamothe.alkemychallengemovie.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.clarkelamothe.alkemychallengemovie.api.ApiServices
import com.clarkelamothe.alkemychallengemovie.api.Retrofit
import com.clarkelamothe.alkemychallengemovie.api.Tmdb
import com.clarkelamothe.alkemychallengemovie.databinding.ActivityMainBinding
import com.clarkelamothe.alkemychallengemovie.viewmodel.MovieAdapter
import com.clarkelamothe.alkemychallengemovie.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity() : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener,
    SearchView.OnQueryTextListener, MovieAdapter.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter
    var movies = mutableListOf<Result>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getPopularMovies()
    }

    private fun getPopularMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = Retrofit.getRetroFit().create(ApiServices::class.java).fetchPopularMovies("${Tmdb.API_KEY}")

            val popularMovies = call.body()

            val results = (popularMovies?.results) ?: emptyList()

            updateMovies(movies, results)

            runOnUiThread {
                if (call.isSuccessful && results.isNotEmpty() ) {
                    initializeRV(results)
                } else {
                    Toast.makeText(this@MainActivity, "Oops! Can't seem to find a movie or something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getMovieByName(name: String?) {
        val name = name ?: ""
        CoroutineScope(Dispatchers.IO).launch {
            val call = Retrofit.getRetroFit().create(ApiServices::class.java).fetchMovieByName(name,
                Tmdb.API_KEY
            )
            val searchedMovie = call.body()
            val results = (searchedMovie?.results) ?: emptyList()
            updateMovies(movies, results)
            runOnUiThread {
                if (call.isSuccessful && results.isNotEmpty()) {
                    adapter.notifyDataSetChanged()
                    initializeRV(results)
                } else {
                    Toast.makeText(this@MainActivity, "Oops! Can't seem to find a movie or something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initializeRV(movies: List<Result>) {
        adapter = MovieAdapter(movies, this)
        binding.rvPopularMovies.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.svMovie.setOnQueryTextListener(this)
        binding.rvPopularMovies.adapter = adapter
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        if (!text.isNullOrEmpty()){
            getMovieByName(text)
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean = true

    override fun onItemClick(position: Int) {
        val intent = Intent(this@MainActivity, MovieDetails::class.java)

        intent.putExtra("movie_id", movieInPosition(position, movies).id)
        intent.putExtra("title", movieInPosition(position, movies).originalTitle)
        intent.putExtra("overview", movieInPosition(position, movies).overview)
        intent.putExtra("imgUrl", movieInPosition(position, movies).posterPath)
        intent.putExtra("backDrop", movieInPosition(position, movies).backdropPath)
        intent.putExtra("language", movieInPosition(position, movies).originalLanguage)
        intent.putExtra("rating", movieInPosition(position, movies).voteAverage)

        startActivity(intent)
    }

    private fun movieInPosition(position: Int, movies: List<Result>): Result = movies[position]

    private fun updateMovies(movies: MutableList<Result>, newList: List<Result>){
        movies.clear()
        movies.addAll(newList)
    }
}

