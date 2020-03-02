package com.example.madesubmission1.external.widget

import android.content.Intent
import android.widget.RemoteViewsService

/**
 * Created by Franz Andel on 2020-02-23.
 * Android Engineer
 */

class StackWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
        StackRemoteViewsFactory(this.applicationContext)
}