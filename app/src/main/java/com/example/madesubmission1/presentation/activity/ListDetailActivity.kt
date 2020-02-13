package com.example.madesubmission1.presentation.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.madesubmission1.R
import com.example.madesubmission1.data.entities.api.MovieAPI
import com.example.madesubmission1.data.entities.api.TvShowAPI
import com.example.madesubmission1.data.entities.api.base.BaseAPI
import com.example.madesubmission1.presentation.viewmodel.ListDetailViewModel
import com.example.madesubmission1.utils.AppConst
import kotlinx.android.synthetic.main.activity_list_detail.*

class ListDetailActivity : AppCompatActivity() {

    private lateinit var listDetailViewModel: ListDetailViewModel
    private lateinit var menu: Menu
    private var isCurrentFavorited = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)

        initializeVM()
        setupToolbar()
        setupUI()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu!!
        setupFavoriteButton()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            val baseAPI = getBaseAPI()

            if (baseAPI != null) {
                if (baseAPI is MovieAPI) {
                    if (isCurrentFavorited) {
                        updateMovieAPIFavoriteInDB(baseAPI, false)
                    } else {
                        updateMovieAPIFavoriteInDB(baseAPI, true)
                    }
                } else if (baseAPI is TvShowAPI) {
                    if (isCurrentFavorited) {
                        updateTvShowAPIFavoriteInDB(baseAPI, false)
                    } else {
                        updateTvShowAPIFavoriteInDB(baseAPI, true)
                    }
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateMovieAPIFavoriteInDB(movieAPI: MovieAPI, state: Boolean) {
        movieAPI.isFavorite = state
        isCurrentFavorited = state
        listDetailViewModel.updateMovieAPIIsFavoriteInDB(movieAPI)
        changeFavoriteIcon(state)
    }

    private fun updateTvShowAPIFavoriteInDB(tvShowAPI: TvShowAPI, state: Boolean) {
        tvShowAPI.isFavorite = state
        isCurrentFavorited = state
        listDetailViewModel.updateTvShowAPIIsFavoriteInDB(tvShowAPI)
        changeFavoriteIcon(state)
    }

    private fun setupFavoriteButton() {
        val baseAPI = getBaseAPI()

        if (baseAPI != null) {
            if (baseAPI is MovieAPI) {
                listDetailViewModel.isMovieAPIFavoriteInDB(baseAPI) {
                    isCurrentFavorited = it
                    changeFavoriteIcon(it)
                }
            } else if (baseAPI is TvShowAPI) {
                listDetailViewModel.isTvShowAPIFavoriteInDB(baseAPI) {
                    isCurrentFavorited = it
                    changeFavoriteIcon(it)
                }
            }
        }
    }

    private fun changeFavoriteIcon(clicked: Boolean) {
        if (clicked) {
            menu.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_black)
        } else {
            menu.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black)
        }
    }

    private fun getBaseAPI(): BaseAPI? {
        return ListDetailActivityArgs.fromBundle(intent.extras as Bundle).baseAPI
    }

    private fun initializeVM() {
        listDetailViewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                ListDetailViewModel::class.java
            )
    }

    private fun setupToolbar() {
        val baseAPI = getBaseAPI()

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            if (baseAPI != null) {
                if (baseAPI is MovieAPI) {
                    title = resources.getString(R.string.toolbar_detail_name, baseAPI.title)
                } else if (baseAPI is TvShowAPI) {
                    title = resources.getString(R.string.toolbar_detail_name, baseAPI.name)
                }
            }
        }
    }

    private fun setupUI() {
        val baseAPI = getBaseAPI()

        if (baseAPI != null) {
            if (baseAPI is MovieAPI) {
                baseAPI.apply {
                    tvListDetailName.text = title
                    tvListDetailReleaseDate.text = releaseDate
                    tvListDetailTopCast.text = originalTitle
                }
            } else if (baseAPI is TvShowAPI) {
                baseAPI.apply {
                    tvListDetailName.text = name
                    tvListDetailReleaseDate.text = firstAirDate
                    tvListDetailTopCast.text = originalName
                }
            }

            Glide.with(this)
                .load(AppConst.baseUrlImage + baseAPI.backdropPath)
                .into(ivListDetailPhoto)
            tvListDetailDescription.text = baseAPI._overview
        }
    }
}
