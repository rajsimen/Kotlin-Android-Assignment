package com.backbase.assignment.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.ui.model.MovieDetails
import com.backbase.assignment.ui.movie.GenreAdapter
import com.bumptech.glide.Glide
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MovieDetailScreenFragment : Fragment() {

    private var movieDetails:MovieDetails?=null
    companion object {
        @JvmStatic
        fun newInstance(movieDetails: MovieDetails) = MovieDetailScreenFragment().apply {
            arguments = Bundle().apply {
                putParcelable("MOVIE_DETAIL",movieDetails)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        movieDetails = arguments?.getParcelable("MOVIE_DETAIL")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_detail,container,false)

        getWidgets(view)
        return view
    }

    fun getWidgets(view: View){

        var posterImageView = view.findViewById(R.id.moviePosterImageView) as ImageView
        var movieTitle = view.findViewById(R.id.movieTitle) as TextView
        var releaseDate = view.findViewById(R.id.releaseDate) as TextView
        var movieDescp = view.findViewById(R.id.moviewDescp) as TextView

        var recyclerView = view.findViewById(R.id.recyclerView)as RecyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, false)

       var genreAdapter = GenreAdapter(activity!!,movieDetails!!.genres)
        recyclerView.adapter = genreAdapter

        movieTitle.text = movieDetails?.title
        releaseDate.text = dateTimeFormate(movieDetails?.release_date!!,movieDetails?.runtime.toString())
        movieDescp.text = movieDetails?.overview

        Glide.with(activity!!).load("https://image.tmdb.org/t/p/original/"+movieDetails?.poster_path)
            .placeholder(activity!!.getDrawable(R.drawable.normal_progress_bar))
            .into(posterImageView)



    }

    fun dateTimeFormate(date:String,time:String):String{
        val formatter =
            DateTimeFormatter.ofPattern("MMMM dd, YYYY")

        val d: Duration = Duration.ofMinutes(time.toLong())

        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE).format(formatter)+"  -  "+ d.toHours()+"h  "+d.toMinutes()+"m"
    }
}