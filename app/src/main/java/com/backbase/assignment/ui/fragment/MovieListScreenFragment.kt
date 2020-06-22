package com.backbase.assignment.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.ui.*
import com.backbase.assignment.ui.model.MovieDetails
import com.backbase.assignment.ui.model.PlayingMovies
import com.backbase.assignment.ui.model.PopularMovies
import com.backbase.assignment.ui.movie.MoviesAdapter
import com.backbase.assignment.ui.network.ApiService
import com.backbase.assignment.ui.other.Constants
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.reflect.Type


class MovieListScreenFragment : Fragment(),MovieDetailListener,MovieResultViewListener {

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var recyclerView: RecyclerView
    private var mCompositeDisposable: CompositeDisposable? = null
    private var moviePresenter:MoviePresenter?=null
    private var progressBar : ProgressBar?= null
    private val PAGE_START = 1
    private var isLoading = false
    private var isLastPage = false
    private var TOTAL_PAGES = 1
    private var currentPage = PAGE_START


    companion object {
        fun newInstance(): MovieListScreenFragment =
            MovieListScreenFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCompositeDisposable = CompositeDisposable()
        moviePresenter = MoviePresenter(SchedulerProvider,this,ApiService.buildService())


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_recycler_view,container,false)

        getWidgets(view)
        return view
    }

    fun getWidgets(view: View){
        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar2)

        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        moviesAdapter = MoviesAdapter(activity!!,this)
        recyclerView.adapter = moviesAdapter
        fetchMovies()


        recyclerView?.addOnScrollListener(object : PaginationScrollListener((recyclerView.layoutManager)as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                getMoreItems()
            }

            override fun getTotalPageCount(): Int {
                return TOTAL_PAGES
            }
        })



    }

    override fun showData(data: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message:String) {
        progressBar?.visibility = View.GONE
        Log.e("ERROR",message)
    }

    override fun getPopularMovieList(popularMovies: PopularMovies) {
        setUpThePopularMovieList(popularMovies)
    }

    override fun getMorePopularMovieList(popularMovies: PopularMovies) {
        getLoadMoreItems(popularMovies)
    }

    override fun getPlayingMovieList(playingMovies: PlayingMovies) {
        setUpThePlayingMovieList(playingMovies)
    }

    override fun getMovieDetail(movieDetails: MovieDetails) {
        setUpTheMovieDetail(movieDetails)
    }

    fun getMoreItems() {

        Log.d("TOTALPAGE",currentPage.toString())

        moviePresenter!!.getMorePopularMovieList(Constants.API_KEY,currentPage.toString())

    }

    private fun getLoadMoreItems(popularMovies: PopularMovies){
        isLoading = false
        moviesAdapter.setPopularMovieList(popularMovies.results)

        moviesAdapter.removeLoadingFooter()
        isLoading = false
        moviesAdapter.setPopularMovieList(popularMovies.results)
        if (currentPage != TOTAL_PAGES) moviesAdapter!!.addLoadingFooter() else isLastPage = true
    }

    private fun fetchMovies() {
        progressBar?.progress = View.VISIBLE
        moviePresenter!!.getPopularMovieList(Constants.API_KEY,currentPage.toString())
        moviePresenter!!.getPlayingMovieList(Constants.API_KEY)

    }

    fun setUpThePopularMovieList(popularMovies : PopularMovies){

        Log.d("LIST",popularMovies.results[0].original_title)
        Log.d("LIST",popularMovies.total_results.toString())
        Log.d("LIST",popularMovies.total_pages.toString())
        TOTAL_PAGES = popularMovies.total_pages

        progressBar!!.visibility = View.GONE
        moviesAdapter.setPopularMovieList(popularMovies.results)
        if (currentPage <= TOTAL_PAGES) moviesAdapter!!.addLoadingFooter() else isLastPage = true

    }

    fun setUpThePlayingMovieList(playingMovies: PlayingMovies){

        Log.d("LIST",playingMovies.results[0].original_title)
        moviesAdapter.setPlayingMovieList(playingMovies.results)
    }

    fun setUpTheMovieDetail(movieDetails: MovieDetails){
        progressBar?.visibility = View.GONE
        Log.d("LISTTTT",movieDetails.original_title)
        var gson =Gson()
        val type: Type = object : TypeToken<MovieDetails?>() {}.type
        val json: String = gson.toJson(movieDetails, type)


//        activity!!.startActivity(Intent(activity,(activity as MainActivity)
//            .getFramentClass(Constants.MOVE_DETAIL_FRAGMENTNAME,movieDetails)::class.java))

//        activity?.let{
            val intent = Intent(activity,MainActivity::class.java).apply {
                putExtra("FRAGMENT_CLASS_NAME", "MovieDetailScreenFragment")
                putExtra("MOVIE_DETAIL",json)
            }
            startActivity(intent)
//            intent.putExtra("FRAGMENT_CLASS_NAME", "HI")
//            intent.putExtra("MOVIE_DETAIL",movieDetails)
//            it.startActivity(intent)
//        }
//        activity!!.startActivity(Intent(activity!!,(activity as MainActivity).newIntent(activity,Constants.MOVE_DETAIL_FRAGMENTNAME,movieDetails)))


////        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.fragment_movie_detail, null)
////        //AlertDialogBuilder
////        val mBuilder = AlertDialog.Builder(activity!!, R.style.WideDialog)
////            .setView(mDialogView)
//
////        val  mAlertDialog = mBuilder.show()
//
//
//        val adb =
//            AlertDialog.Builder(activity!!)
//        val d: Dialog = adb.setView(LayoutInflater.from(activity).inflate(R.layout.fragment_movie_detail, null)).create()
//        // (That new View is just there to have something inside the dialog that can grow big enough to cover the whole screen.)
//
//        // (That new View is just there to have something inside the dialog that can grow big enough to cover the whole screen.)
//        val lp = WindowManager.LayoutParams()
//        lp.copyFrom(d.window!!.attributes)
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT
//        d.show()
//        d.window!!.attributes =lp


    }

    fun getWidgets(movieDetails: MovieDetails,view: View){
//        view.setBackgroundColor(0x00000000)

        var posterImageView = view.findViewById(R.id.moviePosterImageView) as ImageView
        var movieTitle = view.findViewById(R.id.movieTitle) as TextView
        var releaseDate = view.findViewById(R.id.releaseDate) as TextView
        var movieDescp = view.findViewById(R.id.moviewDescp) as TextView

        movieTitle.text = movieDetails?.title
        releaseDate.text = movieDetails?.release_date
        movieDescp.text = movieDetails?.overview

        Glide.with(activity!!).load("https://image.tmdb.org/t/p/original/"+movieDetails?.poster_path)
            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
            .into(posterImageView)


//        val linearLayout = view.findViewById(R.id.genres) as LinearLayout
////        val button_dynamic = view.findViewById(R.id.button) as Button
//        movieDetails.genres.forEach {
//            var button_dynamic = Button(activity)
//            button_dynamic.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100)
//            button_dynamic.setBackgroundResource(R.drawable.custome_button)
//            button_dynamic.textSize = 12f
//            button_dynamic.text = it.name
//            linearLayout.addView(button_dynamic)
//        }




    }

    override fun getMovieDetail(id: Int) {
        progressBar?.visibility = View.VISIBLE
        moviePresenter!!.getMovieDetails(id.toString(),Constants.API_KEY)
    }


    abstract class PaginationScrollListener(var layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

        abstract fun isLastPage(): Boolean

        abstract fun isLoading(): Boolean

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            if (!isLoading() && !isLastPage()) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    loadMoreItems()
                }
            }
        }
        abstract fun loadMoreItems()
        abstract fun getTotalPageCount(): Int
    }
}