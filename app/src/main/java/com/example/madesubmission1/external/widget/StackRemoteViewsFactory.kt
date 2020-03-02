package com.example.madesubmission1.external.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.FutureTarget
import com.example.madesubmission1.R
import com.example.madesubmission1.data.AppRepository
import com.example.madesubmission1.data.db.AppDatabase
import com.example.madesubmission1.data.entities.api.MovieAPI
import com.example.madesubmission1.utils.AppConst

/**
 * Created by Franz Andel on 2020-02-23.
 * Android Engineer
 */

class StackRemoteViewsFactory(private val context: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private val appRepository = AppRepository(AppDatabase(context.applicationContext))

    private lateinit var listFavoriteMovieAPI: List<MovieAPI>

    override fun onCreate() {}

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()
        listFavoriteMovieAPI = appRepository.getAllFavoriteMovieAPIFromDB()
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val currentBitmap = getCurrentBitmap(position) ?: BitmapFactory.decodeResource(
            context.resources,
            R.drawable.ic_nicefilm_placeholder
        )

        val remoteViews = RemoteViews(context.packageName, R.layout.widget_item)
        remoteViews.setImageViewBitmap(R.id.imageView, currentBitmap)

        val extras =
            bundleOf(ImageBannerWidget.EXTRA_MOVIE_NAME to listFavoriteMovieAPI[position].title)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return remoteViews
    }

    override fun getCount(): Int {
        return listFavoriteMovieAPI.size
    }

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {}

    private fun getCurrentBitmap(position: Int): Bitmap? {
        return try {
            val futureBitmap: FutureTarget<Bitmap> = Glide.with(context)
                .asBitmap()
                .override(512, 512)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(AppConst.baseUrlImage + listFavoriteMovieAPI[position].backdropPath)
                .submit()

            futureBitmap.get()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}