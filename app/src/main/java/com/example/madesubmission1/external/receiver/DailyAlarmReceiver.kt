package com.example.madesubmission1.external.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.madesubmission1.R
import com.example.madesubmission1.external.helper.NotificationHelper

class DailyAlarmReceiver : BroadcastReceiver() {

    private val notificationHelper = NotificationHelper()

    override fun onReceive(context: Context, intent: Intent) {
        val title = context.getString(R.string.pn_daily_reminder_title)
        val content = context.getString(R.string.pn_daily_reminder_description)
        notificationHelper.showNotification(context, title, content)
    }
}
