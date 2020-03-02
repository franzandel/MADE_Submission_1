package com.example.madesubmission1.presentation.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.madesubmission1.R
import com.example.madesubmission1.external.alarmmanager.DailyAlarmManager
import com.example.madesubmission1.external.alarmmanager.ReleaseAlarmManager
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_settings)

        setupToolbar()
        setSwitchValue()
        setupSwitchListener()
        setupSpinnerAdapter()
        setSpinnerValue()
        setupSpinnerListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = resources.getString(R.string.toolbar_settings)
        }
    }

    private fun setSwitchValue() {
        val isDailyReminderWanted = RootActivity.appSession.getIsDailyReminderWanted()
        val isReleaseReminderWanted = RootActivity.appSession.getIsReleaseReminderWanted()
        swDailyReminder.isChecked = isDailyReminderWanted
        swReleaseReminder.isChecked = isReleaseReminderWanted
    }

    private fun setupSwitchListener() {
        swDailyReminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                dailyAlarmManager.setRepeatingAlarm(applicationContext)
            } else {
                dailyAlarmManager.cancelAlarm(applicationContext)
            }

            RootActivity.appSession.setIsDailyReminderWanted(isChecked)
        }

        swReleaseReminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                releaseAlarmManager.setRepeatingAlarm(applicationContext)
            } else {
                releaseAlarmManager.cancelAlarm(applicationContext)
            }

            RootActivity.appSession.setIsReleaseReminderWanted(isChecked)
        }
    }

    private fun setupSpinnerAdapter() {
        ArrayAdapter.createFromResource(
            this,
            R.array.language_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spLanguage.adapter = adapter
        }
    }

    private fun setSpinnerValue() {
        when (RootActivity.appSession.getLanguage()) {
            getString(R.string.language_bahasa_value) -> {
                spLanguage.setSelection(0)
            }
            else -> {
                spLanguage.setSelection(1)
            }
        }
    }

    private fun setupSpinnerListener() {
        spLanguage.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val language = when (position) {
                    0 -> getString(R.string.language_bahasa_value)
                    else -> getString(R.string.language_english_value)
                }

                RootActivity.appSession.setLanguage(language)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}
