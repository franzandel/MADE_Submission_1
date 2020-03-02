package com.example.favoriteshower.presentation.activity

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.appcompat.app.AppCompatActivity
import com.example.favoriteshower.R
import com.example.favoriteshower.external.provider.contract.DatabaseContract.EntertainmentColumns.Companion.MOVIE_TABLE_NAME
import com.example.favoriteshower.external.provider.contract.DatabaseContract.EntertainmentColumns.Companion.getContentURI
import com.example.favoriteshower.presentation.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_root.*

class RootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        setToolbarTitle(getString(R.string.toolbar_favorite_movie_name))
        setupTabLayout()
        addTabLayoutListener()
        removeToolbarElevation()
        registerContentObserver()
    }

    private fun setToolbarTitle(title: String) {
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
                    setToolbarTitle(getString(R.string.toolbar_favorite_movie_name))
                } else if (tab?.position == 1) {
                    setToolbarTitle(getString(R.string.toolbar_favorite_tv_show_name))
                }
            }
        })
    }

    private fun removeToolbarElevation() {
        supportActionBar?.elevation = 0f
    }

    private fun getHandler(): Handler {
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        return Handler(handlerThread.looper)
    }

    private fun getObserver(handler: Handler): ContentObserver {
        return object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                setupTabLayout()
            }
        }
    }

    private fun registerContentObserver() {
        contentResolver?.registerContentObserver(
            getContentURI(MOVIE_TABLE_NAME),
            true,
            getObserver(getHandler())
        )
    }
}
