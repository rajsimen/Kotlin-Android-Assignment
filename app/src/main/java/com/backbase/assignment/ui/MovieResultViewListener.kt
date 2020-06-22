package com.backbase.assignment.ui

import com.backbase.assignment.ui.model.MovieDetails
import com.backbase.assignment.ui.model.PlayingMovies
import com.backbase.assignment.ui.model.PopularMovies

/**
 * Created by Paulina Sadowska on 16.05.2018.
 */
interface MovieResultViewListener {
    fun showData(data: Int)
    fun showError(message:String)
    fun getPopularMovieList(popularMovies: PopularMovies)
    fun getMorePopularMovieList(popularMovies: PopularMovies)
    fun getPlayingMovieList(playingMovies: PlayingMovies)
    fun getMovieDetail(movieDetails: MovieDetails)
}