package com.example.madesubmission1.presentation.adapter.recyclerview

import com.bumptech.glide.Glide
import com.example.madesubmission1.data.entities.local.base.BaseLocalModel
import com.example.madesubmission1.presentation.adapter.base.BaseAdapter
import com.example.madesubmission1.presentation.callback.OnLocalItemCallback

/**
 * Created by Franz Andel on 2019-12-29.
 * Android Engineer
 */

class ListLocalAdapter(private val listBaseLocalModel: ArrayList<BaseLocalModel>) :
    BaseAdapter<BaseLocalModel>(listBaseLocalModel) {

    private lateinit var onLocalItemCallback: OnLocalItemCallback

    fun setOnLocalItemClickCallback(onLocalItemCallback: OnLocalItemCallback) {
        this.onLocalItemCallback = onLocalItemCallback
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val baseEntities = listBaseLocalModel[position]

        holder.apply {
            tvListName.text = baseEntities.name
            tvListDescription.text = baseEntities.description

            Glide.with(itemView.context)
                .load(baseEntities.photo)
                .into(ivListPhoto)

            itemView.setOnClickListener {
                onLocalItemCallback.onItemClicked(baseEntities)
            }
        }
    }
}