package com.example.madesubmission1.presentation.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madesubmission1.R
import com.example.madesubmission1.data.entities.api.MovieAPI
import com.example.madesubmission1.data.entities.api.base.BaseAPI
import com.example.madesubmission1.utils.AppConst
import kotlinx.android.synthetic.main.item_list.view.*

/**
 * Created by Franz Andel on 2020-02-27.
 * Android Engineer
 */

class ListAdapter() : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var movies: MutableList<BaseAPI>? = mutableListOf()

    fun addMovies(listMovie: List<BaseAPI>) {
        movies?.clear()
        movies?.addAll(listMovie)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return movies?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies?.get(position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(baseAPI: BaseAPI?) {
            (baseAPI as MovieAPI)
            itemView.tvListName.text = baseAPI.title
            itemView.tvListDescription.text = baseAPI._overview

            Glide.with(itemView.context)
                .load(AppConst.baseUrlImage + baseAPI.backdropPath)
                .into(itemView.ivListPhoto)
        }

    }
}

