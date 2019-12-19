package com.example.madesubmission1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.item_movie.ivMoviePhoto
import kotlinx.android.synthetic.main.item_movie.tvMovieDescription
import kotlinx.android.synthetic.main.item_movie.tvMovieName

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        setIntentData()
        setToolbarName()
        setupUI()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setIntentData() {
        movie = intent.getParcelableExtra(EXTRA_MOVIE) as Movie
    }

    private fun setToolbarName() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "${movie.name} Detail"
        }
    }

    private fun setupUI() {
        ivMoviePhoto.setImageResource(movie.photo)
        tvMovieName.text = movie.name
        tvMovieDescription.text = movie.description
        tvMovieReleaseDate.text = movie.release_date
        tvMovieTopCast.text = movie.top_cast
    }
}
