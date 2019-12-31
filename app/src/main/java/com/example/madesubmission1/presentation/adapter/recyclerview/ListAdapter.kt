package com.example.madesubmission1.presentation.adapter.recyclerview

import com.bumptech.glide.Glide
import com.example.madesubmission1.data.entities.base.BaseEntities
import com.example.madesubmission1.presentation.OnItemCallback
import com.example.madesubmission1.presentation.adapter.base.BaseAdapter

/**
 * Created by Franz Andel on 2019-12-29.
 * Android Engineer
 */

class ListAdapter(private val listBaseEntities: ArrayList<BaseEntities>) :
    BaseAdapter<BaseEntities>(listBaseEntities) {

    private lateinit var onItemClickCallback: OnItemCallback

    fun setOnItemClickCallback(onItemCallback: OnItemCallback) {
        this.onItemClickCallback = onItemCallback
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val baseEntities = listBaseEntities[position]

        holder.tvListName.text = baseEntities.name
        holder.tvListDescription.text = baseEntities.description

        Glide.with(holder.itemView.context)
            .load(baseEntities.photo)
            .into(holder.ivListPhoto)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(baseEntities)
        }
    }
}