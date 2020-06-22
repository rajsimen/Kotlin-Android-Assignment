package com.backbase.assignment.ui.network

import com.backbase.assignment.ui.model.MovieDetails
import com.backbase.assignment.ui.model.PlayingMovies
import com.backbase.assignment.ui.model.PopularMovies
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {


    fun getSomeRemoteData(): Single<Int>

    @GET("/3/movie/popular")
    fun getPopularMovieListResponse(@Query("api_key") key: String,@Query("page") page: String): Observable<PopularMovies>

    @GET("/3/movie/now_playing")
    fun getPlayingMovieListResponse(@Query("api_key") key: String): Observable<PlayingMovies>

    @GET("/3/movie/{MOVIE_ID}")
    fun getMovieDetaisResponse(@Path("MOVIE_ID") id:String, @Query("api_key") key: String): Observable<MovieDetails>



}