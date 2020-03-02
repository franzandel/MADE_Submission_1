package com.example.madesubmission1.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.madesubmission1.R
import com.example.madesubmission1.data.entities.session.AppSession
import com.example.madesubmission1.external.alarmmanager.DailyAlarmManager
import com.example.madesubmission1.external.alarmmanager.ReleaseAlarmManager
import com.example.madesubmission1.presentation.adapter.ViewPagerAdapter
import com.example.madesubmission1.presentation.fragment.ListFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_root.*
import java.util.*

class RootActivity : AppCompatActivity() {

    companion object {
        lateinit var appSession: AppSession
    }

    private val dailyAlarmManager by lazy {
        DailyAlarmManager(
            applicationContext
        )
    }
    private val releaseAlarmManager by lazy {
        ReleaseAlarmManager(
            applicationContext
        )
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
        setRepeatingAlarm()
        setReleaseAlarm()
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
            R.id.action_search -> {
                val searchView = item.actionView as SearchView
                searchView.queryHint = resources.getString(R.string.search_hint)
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(query: String?): Boolean {
                        processingSearchText(query)
                        return false
                    }
                })
            }
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
                val intent = Intent(this, SettingsActivity::class.java)
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

    private fun setRepeatingAlarm() {
        if (appSession.getIsDailyReminderWanted()) {
            if (!dailyAlarmManager.isAlarmSet(applicationContext)) {
                dailyAlarmManager.setRepeatingAlarm(applicationContext)
            }
        }
    }

    private fun setReleaseAlarm() {
        if (appSession.getIsReleaseReminderWanted()) {
            if (!releaseAlarmManager.isAlarmSet(applicationContext)) {
                releaseAlarmManager.setRepeatingAlarm(applicationContext)
            }
        }
    }

    private fun processingSearchText(query: String?) {
        // supportFragmentManager.fragments[0] = NavHostFragment
        val movieFragment = supportFragmentManager.fragments[1] as ListFragment
        val tvShowFragment = supportFragmentManager.fragments[2] as ListFragment

        if (query.isNullOrEmpty()) {
            if (movieFragment.isResumed) {
                movieFragment.setArrData()
            } else {
                tvShowFragment.setArrData()
            }
        } else {
            if (movieFragment.isResumed) {
                movieFragment.searchMovieFromAPI(query)
            } else {
                tvShowFragment.searchTvShowFromAPI(query)
            }
        }
    }
}
