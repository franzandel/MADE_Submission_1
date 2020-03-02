package com.example.madesubmission1.external.alarmmanager

import android.content.Context
import android.content.Intent
import com.example.madesubmission1.external.alarmmanager.base.BaseAlarmManager
import com.example.madesubmission1.external.helper.CalendarHelper
import com.example.madesubmission1.external.receiver.ReleaseAlarmReceiver

/**
 * Created by Franz Andel on 02/03/20.
 * Android Engineer
 */

class ReleaseAlarmManager(private val applicationContext: Context) : BaseAlarmManager() {

    private val calendarHelper = CalendarHelper()

    override fun getIntent(): Intent {
        return Intent(applicationContext, ReleaseAlarmReceiver::class.java)
    }

    override fun getTimeToFirstTrigger(): Long {
        return calendarHelper.getExpectedTimeInterval(8, 0)
    }

}