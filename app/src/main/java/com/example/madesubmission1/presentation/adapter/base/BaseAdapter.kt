package com.example.madesubmission1.presentation.adapter.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madesubmission1.R

/**
 * Created by Franz Andel on 2019-12-29.
 * Android Engineer
 */

abstract class BaseAdapter<T>(private val list: ArrayList<T>) :
    RecyclerView.Adapter<BaseAdapter<T>.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvListName: TextView = itemView.findViewById(R.id.tvListName)
        var tvListDescription: TextView = itemView.findViewById(R.id.tvListDescription)
        var ivListPhoto: ImageView = itemView.findViewById(R.id.ivListPhoto)
    }
}