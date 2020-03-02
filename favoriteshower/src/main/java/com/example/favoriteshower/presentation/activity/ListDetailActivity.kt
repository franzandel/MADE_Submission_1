package com.example.favoriteshower.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.favoriteshower.R
import com.example.favoriteshower.data.entities.api.MovieAPI
import com.example.favoriteshower.data.entities.api.TvShowAPI
import com.example.favoriteshower.data.entities.api.base.BaseAPI
import com.example.favoriteshower.utils.AppConst
import kotlinx.android.synthetic.main.activity_list_detail.*

class ListDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)

        setupToolbar()
        setupUI()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getBaseAPI(): BaseAPI? {
        return ListDetailActivityArgs.fromBundle(intent.extras as Bundle).baseAPI
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
