package com.backbase.assignment.ui.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.ui.MovieDetailListener
import com.backbase.assignment.ui.model.Result
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_horizont_image_view.view.*

class PlayingMovieAdapter(mContext: Context, mMovieDetailListener: MovieDetailListener, mListOfMovies:List<Result>): RecyclerView.Adapter<PlayingMovieAdapter.PlayingMovieViewHolder>(){
    private var listOfMovies = listOf<Result>()
    private var context:Context?=null
    private var movieDetailListener : MovieDetailListener?=null

    init {
        listOfMovies = mListOfMovies
        context = mContext
        movieDetailListener= mMovieDetailListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayingMovieViewHolder {
        return PlayingMovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_horizont_image_view, parent, false))
    }

    override fun getItemCount(): Int {
        return listOfMovies.size
    }

    override fun onBindViewHolder(holder: PlayingMovieViewHolder, position: Int) {
        val result = listOfMovies[position]
        holder.bindView(result)
    }

  inner  class PlayingMovieViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        lateinit var imageView: ImageView
        lateinit var progressBar: ProgressBar

        fun bindView(result: Result){
            imageView = itemView.findViewById(R.id.imageView)
            progressBar = itemView.findViewById(R.id.progressBar)


            Glide.with(itemView.context).load("https://image.tmdb.org/t/p/original/"+result.poster_path!!)
                .placeholder(context!!.getDrawable(R.drawable.normal_progress_bar))
                .into(itemView.imageView)

            progressBar.visibility= View.GONE
        }
    }
}