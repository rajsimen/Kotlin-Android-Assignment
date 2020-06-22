package com.backbase.assignment.ui.movie

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.ui.MovieDetailListener
import com.backbase.assignment.ui.custom.RatingView
import com.backbase.assignment.ui.model.Result
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_item.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MoviesAdapter(mContext: Context, mMovieDetailListener: MovieDetailListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfMovies: MutableList<Result> = ArrayList()
    private var listOfPlayingMovies = listOf<Result>()
    private var context:Context?=null
    private var movieDetailListener : MovieDetailListener?=null
    private var isLoadingAdded = false


    init {
       context = mContext
        movieDetailListener= mMovieDetailListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

    return when(viewType){
        ViewType.HEADER1.ordinal->HeaderViewHolder1(LayoutInflater.from(parent.context).inflate(R.layout.row_header_layout, parent, false))
        ViewType.HEADER2.ordinal->HeaderViewHolder1(LayoutInflater.from(parent.context).inflate(R.layout.row_header_layout, parent, false))
        ViewType.BODY.ordinal -> PopularMovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false))
        ViewType.FOOTER.ordinal -> ProgressBarHolder(LayoutInflater.from(parent.context).inflate(R.layout.progress_bar_view, parent, false))
        else ->PopularMovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.progress_bar_view, parent, false))
    }
    }

    override fun getItemCount(): Int {
       return if (listOfMovies==null)0 else listOfMovies!!.size+2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            ViewType.HEADER1.ordinal-> {
                val headerViewHolder = holder as HeaderViewHolder1
                headerViewHolder.bindView("Playing Now")

                headerViewHolder.horizontalRecyclerView.visibility = View.VISIBLE
                headerViewHolder.horizontalRecyclerView.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                var playingMovieAdapter = PlayingMovieAdapter(context!!,movieDetailListener!!,listOfPlayingMovies)
                headerViewHolder.horizontalRecyclerView.adapter = playingMovieAdapter
            }
            ViewType.HEADER2.ordinal-> {
                val headerViewHolder2 = holder as HeaderViewHolder1
                headerViewHolder2.bindView("Most Popular")
            }
            ViewType.BODY.ordinal -> {
                val popularMoviesResult = listOfMovies!![position-2]
                val popularMovieViewHolder = holder as PopularMovieViewHolder
                popularMovieViewHolder.bindView(popularMoviesResult)
            }
            ViewType.FOOTER.ordinal -> {

            }
        }
    }

    inner class PopularMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var poster: ImageView
        lateinit var title: TextView
        lateinit var releaseDate: TextView
        lateinit var rating: RatingView
        lateinit var progressBar: ProgressBar
        lateinit var percentage : TextView



        fun bindView(result: Result) {
            poster = itemView.findViewById(R.id.poster)
            progressBar = itemView.findViewById(R.id.progressBar)
            rating = itemView.findViewById(R.id.rating)
            percentage= itemView.findViewById(R.id.percentage)
//            poster.setImageURI(Uri.parse("https://image.tmdb.org/t/p/original/${item["poster_path"].asString}"))
            Glide.with(itemView.context).load("https://image.tmdb.org/t/p/original/"+result.poster_path)
                .placeholder(context!!.getDrawable(R.drawable.normal_progress_bar))
                .into(itemView.poster)
//            Log.d("URL","https://image.tmdb.org/t/p/original/"+result.poster_path)
            rating.progress = result.vote_average.toString().replace(".","").toInt()
            percentage.text = result.vote_average.toString().replace(".","")+"%"

            if (result.vote_average.toString().replace(".","").toInt()<50) {
                rating.progressDrawable =
                    context!!.getDrawable(R.drawable.circle_progress_bar_yellow)
            }else{
                rating.progressDrawable =
                    context!!.getDrawable(R.drawable.circle_progress_bar)
            }

//            Glide.with(itemView.context)
//                .load("https://image.tmdb.org/t/p/original/"+result.poster_path!!)
//                .listener(object : RequestListener<Drawable> {
//                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                    progressBar.visibility = View.GONE
//                    return false
//                }
//
//                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
//                    progressBar.visibility = View.GONE
//                    return false
//                }
//            })
//                .into(itemView.poster)


            title = itemView.findViewById(R.id.title)
            title.text = result.title

            releaseDate = itemView.findViewById(R.id.releaseDate)
            if (result.release_date!=null)
            releaseDate.text = dateTimeFormate(result.release_date)

            itemView.setOnClickListener{
                movieDetailListener?.getMovieDetail(result.id)
            }
        }
    }

    class HeaderViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var title: TextView
        lateinit var horizontalRecyclerView: RecyclerView

        fun bindView(headerTitle:String) {
            title = itemView.findViewById(R.id.header)
            title.text = headerTitle

            horizontalRecyclerView = itemView.findViewById(R.id.horizontal_recycler_view)

            }
    }

    inner class ProgressBarHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var progressBar: ProgressBar

        fun bindView() {
            progressBar = itemView.findViewById(R.id.progressBar3)

        }
    }

    enum class ViewType(viewType: Int) {
        HEADER1(0),
        HEADER2(1),
        BODY(2),
        FOOTER(3)
    }

    fun setPopularMovieList(listOfMovies: List<Result>) {
        this.listOfMovies!!.addAll(listOfMovies)
        notifyDataSetChanged()
    }

    fun setPlayingMovieList(listOfMovies: List<Result>) {
        this.listOfPlayingMovies = listOfMovies
        notifyDataSetChanged()
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(Result())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = listOfMovies!!.size-1
        val item = getItem(position)
        if (item != null) {
            listOfMovies!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    fun add(result: Result) {
        listOfMovies!!.add(result)
        notifyItemInserted(listOfMovies!!.size)
    }

    fun getItem(position: Int): Result {
        return listOfMovies!![position]
    }

    override fun getItemViewType(position: Int): Int {

        return if (position == listOfMovies!!.size -1 && isLoadingAdded) ViewType.FOOTER.ordinal
        else if (position==0)ViewType.HEADER1.ordinal
        else if (position==1)ViewType.HEADER2.ordinal
        else ViewType.BODY.ordinal
//        return when (position) {
//            0 -> ViewType.HEADER1.ordinal
//            1 -> ViewType.HEADER2.ordinal
//            (listOfMovies.size + 1 && isLoadingAdded)-> ViewType.FOOTER.ordinal
//            else -> ViewType.BODY.ordinal
//        }
    }

    fun dateTimeFormate(date:String):String{
        val formatter =
            DateTimeFormatter.ofPattern("MMMM dd, YYYY")

        return if (date=="") "" else LocalDate.parse(date, DateTimeFormatter.ISO_DATE).format(formatter)
    }

}