package com.example.madesubmission1

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_movie_list.*

class MovieListActivity : AppCompatActivity() {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var arrMovieName: Array<String>
    private lateinit var arrMovieDescription: Array<String>
    private lateinit var arrMovieReleaseDate: Array<String>
    private lateinit var arrMovieTopCast: Array<String>
    private lateinit var arrMoviePhoto: TypedArray
    private var arrMovies = arrayListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        setToolbarName()
        setupLVAdapter()
        setArrData()
        addArrData()
        setupUIClickListener()
    }

    private fun setToolbarName() {
        supportActionBar?.title = "New Movies List"
    }

    private fun setupLVAdapter() {
        movieAdapter = MovieAdapter(this)
        lv_list.adapter = movieAdapter
    }

    private fun setArrData() {
        arrMovieName = resources.getStringArray(R.array.arr_movie_name)
        arrMovieDescription = resources.getStringArray(R.array.arr_movie_description)
        arrMovieReleaseDate = resources.getStringArray(R.array.arr_movie_release_date)
        arrMovieTopCast = resources.getStringArray(R.array.arr_movie_top_cast)
        arrMoviePhoto = resources.obtainTypedArray(R.array.arr_movie_photo)
    }

    private fun addArrData() {
        for (position in arrMovieName.indices) {
            val movie = Movie(
                arrMovieName[position],
                arrMovieDescription[position],
                arrMovieReleaseDate[position],
                arrMovieTopCast[position],
                arrMoviePhoto.getResourceId(position, -1)
            )
            arrMovies.add(movie)
        }

        movieAdapter.movies = arrMovies
    }

    private fun setupUIClickListener() {
        lv_list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val movieDetailIntent = Intent(this, MovieDetailActivity::class.java).apply {
                putExtra(MovieDetailActivity.EXTRA_MOVIE, arrMovies[position])
            }

            startActivity(movieDetailIntent)
        }
    }
}
