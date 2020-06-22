package com.backbase.assignment

import com.backbase.assignment.ui.MoviePresenter
import com.backbase.assignment.ui.MovieResultViewListener
import com.backbase.assignment.ui.TestSchedulerProvider
import com.backbase.assignment.ui.TrampolineSchedulerProvider
import com.backbase.assignment.ui.fragment.MovieListScreenFragment
import com.backbase.assignment.ui.network.ApiInterface
import com.backbase.assignment.ui.other.Constants
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Test

import org.junit.Assert.*
import org.mockito.BDDMockito
import org.mockito.Mockito.mock
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private var view = mock(MovieResultViewListener::class.java)
    private var service = mock(ApiInterface::class.java)
    private var schedulerProvider = TrampolineSchedulerProvider()
    private val testScheduler = TestScheduler()
    private var testSchedulerProvider = TestSchedulerProvider(testScheduler)

    @Test
    fun simpleTestExample_wrong() {
        //given
        val presenter = MoviePresenter(schedulerProvider, view, service)
        BDDMockito.given(service.getSomeRemoteData()).willReturn(Single.just(5))

        //when
        presenter.getMovieDetails()

        //then
        BDDMockito.then(view).should().showData(5)
    }

    @Test
    fun simpleTestExample() {
        //given
        val presenter = MoviePresenter(schedulerProvider, view, service)
        BDDMockito.given(service.getSomeRemoteData()).willReturn(Single.just(5))

        //when
//        presenter.getPlayingMovieList("")
        presenter.getPlayingMovieList("1234")
//        presenter.getPlayingMovieList(Constants.API_KEY)

        //then
//        BDDMockito.then(view).should().showData(5)
        BDDMockito.then(view).should().showError("ERROR")
    }


    @Test
    fun testSubscriberTestExample() {
        //given
        val observable = Observable.just(6).delay(1, TimeUnit.SECONDS, testScheduler)

        val testObserver = TestObserver<Int>()

        //when
        observable.subscribe(testObserver)

        //then
        testScheduler.advanceTimeBy(950, TimeUnit.MILLISECONDS)
        testObserver.assertNotTerminated()
        testScheduler.advanceTimeBy(60, TimeUnit.MILLISECONDS)
        testObserver.assertValue(6)
        testObserver.assertComplete()
    }
}
