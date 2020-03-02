package com.example.madesubmission1.external.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.madesubmission1.data.AppRepository
import com.example.madesubmission1.data.db.AppDatabase
import com.example.madesubmission1.data.entities.api.APIStateHandler
import com.example.madesubmission1.data.entities.api.MovieAPI
import com.example.madesubmission1.external.helper.NotificationHelper
import com.example.madesubmission1.external.helper.NotificationHelper.Companion.MAX_NOTIFICATION
import java.text.SimpleDateFormat
import java.util.*

class ReleaseAlarmReceiver : BroadcastReceiver() {

    private var notificationCount = 0
    private lateinit var appRepository: AppRepository
    private val medldMovie = MediatorLiveData<APIStateHandler<MovieAPI>>()

    private val notificationHelper = NotificationHelper()

    override fun onReceive(context: Context, intent: Intent) {
        appRepository = AppRepository(AppDatabase(context))

        getReleasedTodayMoviesFromAPI()
            .observeForever {
                val movieList = it.baseAPIResponse!!.results

                repeat(movieList.size) {
                    if (notificationCount <= MAX_NOTIFICATION) {
                        notificationHelper.showInboxNotification(
                            context,
                            movieList,
                            notificationCount
                        )
                        notificationCount++
                    }
                }
            }
    }

    private fun getReleasedTodayMoviesFromAPI(): LiveData<APIStateHandler<MovieAPI>> {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        medldMovie.addSource(appRepository.getReleasedTodayMoviesFromAPI(today, today)) {
            medldMovie.value = it
        }

        return medldMovie
    }
}
