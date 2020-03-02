package com.example.madesubmission1.external.helper

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.madesubmission1.R
import com.example.madesubmission1.data.entities.api.MovieAPI
import com.example.madesubmission1.presentation.activity.RootActivity

/**
 * Created by Franz Andel on 02/03/20.
 * Android Engineer
 */

class NotificationHelper {
    companion object {
        private const val CHANNEL_ID = "daily_movie_channel"
        private const val CHANNEL_NAME = "Daily Movie Channel"

        private const val INBOX_CHANNEL_ID = "release_movie_channel"
        private const val INBOX_CHANNEL_NAME = "New Release Movie Channel"
        private const val RELEASE_GROUP_KEY = "release_group_key"
        const val MAX_NOTIFICATION = 3
        private const val NOTIFICATION_REQUEST_CODE = 200
        private const val ID_REPEATING = 101
    }

    fun showNotification(
        context: Context,
        title: String,
        message: String
    ) {
        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_movie_black)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                enableVibration(true)
                vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            }

            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(ID_REPEATING, notification)
    }

    fun showInboxNotification(
        context: Context,
        listMovieAPI: List<MovieAPI>,
        notificationCount: Int
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_movie_black)

        val intent = Intent(context, RootActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val mBuilder: NotificationCompat.Builder

        if (notificationCount < MAX_NOTIFICATION) {
            mBuilder = NotificationCompat.Builder(
                context,
                INBOX_CHANNEL_ID
            )
                .setContentTitle(listMovieAPI[notificationCount].title)
                .setContentText(
                    context.getString(
                        R.string.pn_release_reminder_new_line_description,
                        listMovieAPI[notificationCount].title
                    )
                )
                .setSmallIcon(R.drawable.ic_movie_black)
                .setLargeIcon(largeIcon)
                .setGroup(RELEASE_GROUP_KEY)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        } else {
            val inboxStyle = NotificationCompat.InboxStyle()
                .addLine(
                    context.getString(
                        R.string.pn_release_reminder_new_line_description,
                        listMovieAPI[notificationCount].title
                    )
                )
                .addLine(
                    context.getString(
                        R.string.pn_release_reminder_new_line_description,
                        listMovieAPI[notificationCount - 1].title
                    )
                )
                .addLine(
                    context.getString(
                        R.string.pn_release_reminder_new_line_description,
                        listMovieAPI[notificationCount - 2].title
                    )
                )
                .setBigContentTitle(
                    context.getString(
                        R.string.pn_release_reminder_content_title,
                        notificationCount.toString()
                    )
                )
                .setSummaryText(
                    context.getString(
                        R.string.pn_release_reminder_summary_text,
                        listMovieAPI.size.toString()
                    )
                )

            mBuilder = NotificationCompat.Builder(
                context,
                INBOX_CHANNEL_ID
            )
                .setContentTitle(
                    context.getString(
                        R.string.pn_release_reminder_content_title,
                        notificationCount.toString()
                    )
                )
                .setContentText(context.getString(R.string.pn_release_reminder_content_description))
                .setSmallIcon(R.drawable.ic_movie_black)
                .setGroup(RELEASE_GROUP_KEY)
                .setGroupSummary(true)
                .setContentIntent(pendingIntent)
                .setStyle(inboxStyle)
                .setAutoCancel(true)
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                INBOX_CHANNEL_ID,
                INBOX_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            mBuilder.setChannelId(INBOX_CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = mBuilder.build()
        notificationManager.notify(notificationCount, notification)
    }
}