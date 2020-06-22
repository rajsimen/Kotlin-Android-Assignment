package com.backbase.assignment.ui.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.ui.MovieDetailListener
import com.backbase.assignment.ui.model.Genre

class GenreAdapter (mContext: Context, mListOfGenre:List<Genre>): RecyclerView.Adapter<GenreAdapter.GenreViewHolder>(){
    private var listOfGenre = listOf<Genre>()
    private var context: Context?=null

    init {
        listOfGenre = mListOfGenre
        context = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.genre_button, parent, false))
    }

    override fun getItemCount(): Int {
        return listOfGenre.size
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val result = listOfGenre[position]
        holder.bindView(result)
    }

    inner  class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        lateinit var genreButton: Button

        fun bindView(result: Genre){
            genreButton = itemView.findViewById(R.id.button) as Button
            genreButton.text = result.name

        }
    }
}