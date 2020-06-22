package com.backbase.assignment.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class PopularMovies(
    val page: Int,
    val total_results:Int,
    val total_pages: Int ,
    val results: List<Result>
)

data class PlayingMovies (val results:List<Result>)

data class Result (var popularity:Double=0.0,
                   var voteCount:Int=0,
                   var video:Boolean=false,
                   var poster_path:String="",
                   var id:Int=0,
                   var adult:Boolean=false,
                   var backdrop_path:String="",
                   var original_language:String="",
                   var original_title:String="",
                   var genre_ids: List<Int> = emptyList(),
                   var title: String="",
                   var vote_average: Double=0.0,
                   var overview:String="",
                   var release_date:String="")

@Parcelize
class MovieDetails (
    var adult: Boolean,
    var backdrop_path: String,
    var belongs_to_collection: BelongsToCollection ,
    var budget: Int,
    var genres: List<Genre>,
    var homepage: String,
    var id: Int,
    var imdb_id: String,
    var original_language: String,
    var original_title: String,
    var overview: String,
    var popularity: Double,
    var poster_path: String,
    var production_companies: List<ProductionCompany>,
    var production_countries: List<ProductionCountry>,
    var release_date: String,
    var revenue: Int,
    var runtime: Int,
    var spoken_languages: List<SpokenLanguage>,
    var status: String,
    var tagline: String,
    var title: String,
    var video: Boolean,
    var vote_average: Double,
    var vote_count: Int?
):Parcelable

@Parcelize
class Genre (
    var id: Int,
    var name: String
):Parcelable

@Parcelize
class ProductionCompany (
    var id: Int,
    var logo_path: String,
    var name: String,
    var origin_country: String
):Parcelable

@Parcelize
class ProductionCountry (
    var iso_3166_1: String,
    var name: String
):Parcelable

@Parcelize
class SpokenLanguage (
    var iso_639_1: String,
    var name: String
):Parcelable

@Parcelize
class BelongsToCollection (
    var id: Int,
    var poster_path: String,
    var name: String,
    var backdrop_path: String
):Parcelable