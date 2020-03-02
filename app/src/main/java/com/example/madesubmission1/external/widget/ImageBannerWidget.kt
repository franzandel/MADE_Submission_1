package com.example.madesubmission1.external.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.net.toUri
import com.example.madesubmission1.R
import com.example.madesubmission1.utils.showToast

/**
 * Implementation of App Widget functionality.
 */
class ImageBannerWidget : AppWidgetProvider() {

    companion object {
        private const val TOAST_ACTION = "com.franz.amazing.TOAST_ACTION"
        const val EXTRA_MOVIE_NAME = "com.franz.amazing.EXTRA_MOVIE_NAME"

        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, StackWidgetService::class.java).apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                data = toUri(Intent.URI_INTENT_SCHEME).toUri()
            }

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.image_banner_widget).apply {
                setRemoteAdapter(R.id.stackView, intent)
                setEmptyView(R.id.stackView, R.id.emptyView)
            }

            val toastIntent = Intent(context, ImageBannerWidget::class.java).apply {
                action =
                    TOAST_ACTION
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }

            val toastPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setPendingIntentTemplate(R.id.stackView, toastPendingIntent)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(
                context,
                appWidgetManager,
                appWidgetId
            )
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == TOAST_ACTION) {
            val movieName = intent.getStringExtra(EXTRA_MOVIE_NAME)
            if (!movieName.isNullOrEmpty()) {
                context.showToast(movieName, Toast.LENGTH_LONG)
            }
        }
    }
}