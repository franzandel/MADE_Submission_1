package com.example.madesubmission1.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.madesubmission1.R
import com.example.madesubmission1.data.entities.session.AppSession
import com.example.madesubmission1.presentation.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_root.*
import java.util.*

class RootActivity : AppCompatActivity() {

    companion object {
        lateinit var appSession: AppSession
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appSession = AppSession(this)
        loadLanguage()

        setContentView(R.layout.activity_root)

        setCurrentToolbarTitle()
        setupTabLayout()
        addTabLayoutListener()
        removeToolbarElevation()
    }

    override fun onRestart() {
        super.onRestart()
        loadLanguage()
        this.recreate()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite_page -> {
                if (appSession.getIsShowingFavorite()) {
                    appSession.setIsShowingFavorite(false)
                    item.title = getString(R.string.show_all_settings)
                } else {
                    appSession.setIsShowingFavorite(true)
                    item.title = getString(R.string.show_favorite_settings)
                }

                appSession.setHasUserClickedFavorite(true)
                setupTabLayout()
            }
            R.id.action_change_settings -> {
                val intent = Intent(this, ChangeLanguageActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun setCurrentToolbarTitle() {
        if (appSession.getIsShowingFavorite()) {
            setToolbarTitle(getString(R.string.toolbar_favorite_movie_name))
        } else {
            setToolbarTitle(getString(R.string.toolbar_movie_name))
        }
    }

    private fun setupTabLayout() {
        val sectionsPagerAdapter =
            ViewPagerAdapter(
                this,
                supportFragmentManager
            )
        vp_root.adapter = sectionsPagerAdapter
        tl_root.setupWithViewPager(vp_root)
    }

    private fun addTabLayoutListener() {
        tl_root.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    setCurrentToolbarTitle()
                } else if (tab?.position == 1) {
                    if (appSession.getIsShowingFavorite()) {
                        setToolbarTitle(getString(R.string.toolbar_favorite_tv_show_name))
                    } else {
                        setToolbarTitle(getString(R.string.toolbar_tv_show_name))
                    }
                }
            }
        })
    }

    private fun removeToolbarElevation() {
        supportActionBar?.elevation = 0f
    }

    private fun loadLanguage() {
        val selectedLanguage = appSession.getLanguage()
        setApplicationLanguage(selectedLanguage)
    }

    private fun setApplicationLanguage(language: String) {
        if (language.isNotEmpty()) {
            val activityRes = resources
            val activityConf = activityRes.configuration
            val newLocale = Locale(language)
            activityConf.setLocale(newLocale)
            activityRes.updateConfiguration(
                activityConf,
                activityRes.displayMetrics
            )

            val applicationRes = applicationContext.resources
            val applicationConf = applicationRes.configuration
            applicationConf.setLocale(newLocale)
            applicationRes.updateConfiguration(
                applicationConf,
                applicationRes.displayMetrics
            )
        }
    }
}
