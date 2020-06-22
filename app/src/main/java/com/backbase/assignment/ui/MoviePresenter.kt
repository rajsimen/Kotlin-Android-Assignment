package com.backbase.assignment.ui

import com.backbase.assignment.ui.network.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MoviePresenter(private val schedulerProvider: BaseSchedulerProvider,
                     private val view: MovieResultViewListener,
                     private val service: ApiInterface) {

    fun getPopularMovieList(apikey: String,page: String) {
        service.getSomeRemoteData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { view.showData(it) },
                { view.showError(it.message!!) }
            )
    }

    fun getPlayingMovieList(apikey: String) {
        service.getSomeRemoteData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { view.showData(it) },
                { view.showError(it.message!!) }
            )
    }

    fun getMovieDetails() {
        service.getSomeRemoteData()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                { view.showData(it) },
                { view.showError(it.message!!) }
            )
    }




}