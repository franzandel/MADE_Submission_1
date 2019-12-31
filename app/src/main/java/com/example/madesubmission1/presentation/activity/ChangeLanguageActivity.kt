package com.example.madesubmission1.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.madesubmission1.R
import kotlinx.android.synthetic.main.activity_change_language.*

class ChangeLanguageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_language)

        setupToolbar()
        setCheckedRadioBtn()
        setRadioGroupListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = resources.getString(R.string.toolbar_change_language)
        }
    }

    private fun setCheckedRadioBtn() {
        when (RootActivity.appSession.getLanguage()) {
            resources.getString(R.string.language_bahasa_value) -> {
                rb_bahasa_indonesia.isChecked = true
            }
            else -> {
                rb_english.isChecked = true
            }
        }
    }

    private fun setRadioGroupListener() {
        rg_language.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_bahasa_indonesia -> {
                    RootActivity.appSession.setLanguage(resources.getString(R.string.language_bahasa_value))
                }

                R.id.rb_english -> {
                    RootActivity.appSession.setLanguage(resources.getString(R.string.language_english_value))
                }
            }
        }
    }
}
