package com.example.madesubmission1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by Franz Andel on 2019-12-18.
 * Android Engineer
 */

class MovieAdapter internal constructor(private val context: Context): BaseAdapter() {
    internal var movies = arrayListOf<Movie>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        }

        val viewHolder = ViewHolder(itemView as View)

        val movie = getItem(position) as Movie
        viewHolder.bind(movie)
        return itemView
    }

    override fun getItem(position: Int): Any = movies[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = movies.size

    private inner class ViewHolder internal constructor(view: View) {
        val tvMovieName: TextView = view.findViewById(R.id.tvMovieName)
        val tvMovieDescription: TextView = view.findViewById(R.id.tvMovieDescription)
        val ivMoviePhoto: ImageView = view.findViewById(R.id.ivMoviePhoto)

        internal fun bind(movie: Movie) {
            tvMovieName.text = movie.name
            tvMovieDescription.text = movie.description
            ivMoviePhoto.setImageResource(movie.photo)
        }
    }
}