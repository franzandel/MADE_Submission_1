package com.example.madesubmission1.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.madesubmission1.R
import com.example.madesubmission1.data.entities.Movie
import com.example.madesubmission1.data.entities.TvShow
import com.example.madesubmission1.data.entities.base.BaseEntities
import kotlinx.android.synthetic.main.activity_list_detail.*

class ListDetailActivity : AppCompatActivity() {

    private lateinit var baseEntities: BaseEntities

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)

        setIntentData()
        setupToolbar()
        setupUI()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setIntentData() {
        baseEntities = ListDetailActivityArgs.fromBundle(intent.extras as Bundle).baseEntities
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = resources.getString(R.string.toolbar_detail_name, baseEntities.name)
        }
    }

    private fun setupUI() {
        ivListDetailPhoto.setImageResource(baseEntities.photo)
        tvListDetailName.text = baseEntities.name
        tvListDetailDescription.text = baseEntities.description
        tvListDetailReleaseDate.text = baseEntities.release_date
        tvListDetailTopCast.text = baseEntities.top_cast
    }
}
