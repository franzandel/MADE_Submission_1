package com.example.madesubmission1.external.alarmmanager.base

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

/**
 * Created by Franz Andel on 02/03/20.
 * Android Engineer
 */

abstract class BaseAlarmManager {
    companion object {
        private const val REPEAT_REQUEST_CODE = 101
    }

    abstract fun getIntent(): Intent
    abstract fun getTimeToFirstTrigger(): Long

    fun setRepeatingAlarm(context: Context) {
        val alarmManager = getAlarmManager(context)
        val pendingIntent = getPendingIntent(context)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            getTimeToFirstTrigger(),
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = getAlarmManager(context)
        val pendingIntent = getPendingIntent(context)

        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    fun isAlarmSet(context: Context): Boolean {
        return PendingIntent.getBroadcast(
            context,
            REPEAT_REQUEST_CODE,
            getIntent(),
            PendingIntent.FLAG_NO_CREATE
        ) != null
    }

    private fun getAlarmManager(context: Context): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private fun getPendingIntent(context: Context): PendingIntent {
        return PendingIntent.getBroadcast(context, REPEAT_REQUEST_CODE, getIntent(), 0)
    }
}