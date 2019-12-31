package com.example.madesubmission1.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import com.example.madesubmission1.R
import com.example.madesubmission1.presentation.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_root.*

class RootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        setToolbarName(resources.getString(R.string.toolbar_movie_name))
        setupTabLayout()
        addTabLayoutListener()
        removeToolbarElevation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
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
}
