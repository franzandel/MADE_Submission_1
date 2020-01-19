package com.example.madesubmission1.presentation.adapter.recyclerview

import com.bumptech.glide.Glide
import com.example.madesubmission1.data.entities.api.MovieAPI
import com.example.madesubmission1.data.entities.api.TvShowAPI
import com.example.madesubmission1.data.entities.api.base.BaseAPI
import com.example.madesubmission1.presentation.adapter.base.BaseAdapter
import com.example.madesubmission1.presentation.callback.OnAPIItemCallback
import com.example.madesubmission1.utils.AppConst

/**
 * Created by Franz Andel on 2019-12-29.
 * Android Engineer
 */

class ListAPIAdapter(private val listBaseAPI: ArrayList<BaseAPI>) :
    BaseAdapter<BaseAPI>(listBaseAPI) {

    private lateinit var onAPIItemCallback: OnAPIItemCallback

    fun setOnAPIItemClickCallback(onAPIItemCallback: OnAPIItemCallback) {
        this.onAPIItemCallback = onAPIItemCallback
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val baseAPI = listBaseAPI[position]

        holder.apply {
            if (baseAPI is MovieAPI) {
                tvListName.text = baseAPI.title
                tvListDescription.text = baseAPI._overview

                Glide.with(itemView.context)
                    .load(AppConst.baseUrlImage + baseAPI.backdropPath)
                    .into(ivListPhoto)
            } else if (baseAPI is TvShowAPI) {
                tvListName.text = baseAPI.name
                tvListDescription.text = baseAPI._overview

                Glide.with(itemView.context)
                    .load(AppConst.baseUrlImage + baseAPI.backdropPath)
                    .into(ivListPhoto)
            }


            itemView.setOnClickListener {
                onAPIItemCallback.onItemClicked(baseAPI)
            }
        }
    }
}