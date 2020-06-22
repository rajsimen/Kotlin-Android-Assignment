package com.backbase.assignment.ui

import com.backbase.assignment.ui.network.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MoviePresenter(private val schedulerProvider: BaseSchedulerProvider,
                     private val view: MovieResultViewListener,
                     private val service: ApiInterface) {
    private var mCompositeDisposable: CompositeDisposable? = null

    init {
        mCompositeDisposable = CompositeDisposable()
    }

    fun getPopularMovieList(apikey: String,page: String) {

        mCompositeDisposable!!.add(service.getPopularMovieListResponse(apikey,page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { view.getPopularMovieList(it) },
                { view.showError(it.message!!) }
            ))
    }

    fun getMorePopularMovieList(apikey: String,page: String) {

        mCompositeDisposable!!.add(service.getPopularMovieListResponse(apikey,page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { view.getMorePopularMovieList(it) },
                { view.showError(it.message!!) }
            ))
    }

    fun getPlayingMovieList(apikey: String) {
        mCompositeDisposable!!.add(service.getPlayingMovieListResponse(apikey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { view.getPlayingMovieList(it) },
                { view.showError(it.message!!) }
            ))
    }

    fun getMovieDetails(id:String,apikey: String) {
        mCompositeDisposable!!.add(service.getMovieDetaisResponse(id,apikey)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                { view.getMovieDetail(it) },
                { view.showError(it.message!!) }
            ))
    }




}