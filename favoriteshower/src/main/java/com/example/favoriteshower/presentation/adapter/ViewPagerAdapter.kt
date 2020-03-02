package com.example.favoriteshower.presentation.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.favoriteshower.R
import com.example.favoriteshower.presentation.fragment.ListFragment

/**
 * Created by Franz Andel on 2019-12-25.
 * Android Engineer
 */

class ViewPagerAdapter(private val context: Context, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val arrTabTitles = intArrayOf(
        R.string.tab_title_1,
        R.string.tab_title_2
    )

    override fun getItem(position: Int): Fragment {
        return ListFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(arrTabTitles[position])
    }

    override fun getCount(): Int {
        return 2
    }
}