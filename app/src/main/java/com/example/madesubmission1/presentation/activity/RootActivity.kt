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

        setToolbarName(resources.getString(R.string.toolbar_movie_name))
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
        if (item.itemId == R.id.action_change_settings) {
            val intent = Intent(this, ChangeLanguageActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setToolbarName(title: String) {
        supportActionBar?.title = title
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
                    setToolbarName(resources.getString(R.string.toolbar_movie_name))
                } else if (tab?.position == 1) {
                    setToolbarName(resources.getString(R.string.toolbar_tv_show_name))
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
