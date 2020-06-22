package com.backbase.assignment.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.backbase.assignment.R
import com.backbase.assignment.ui.fragment.MovieDetailScreenFragment
import com.backbase.assignment.ui.fragment.MovieListScreenFragment
import com.backbase.assignment.ui.model.MovieDetails
import com.backbase.assignment.ui.other.Constants
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_base.*


class MainActivity : BaseActivity() {



    private val INTENT_REQUEST_FRAGMENT = "FRAGMENT_CLASS_NAME"


    fun newIntent(context: Context?, mFragmentName: String, movieDetails: MovieDetails): Intent? {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(INTENT_REQUEST_FRAGMENT, mFragmentName)
        intent.putExtra("MOVIE_DETAIL",movieDetails)
        return intent
    }

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val movieDetails:MovieDetails?=null
       if (intent.getStringExtra(INTENT_REQUEST_FRAGMENT)!=null){

           var gson = Gson()
           var movieDetails = gson.fromJson(intent.getStringExtra("MOVIE_DETAIL"), MovieDetails::class.java)
           getFramentClass(intent?.getStringExtra(INTENT_REQUEST_FRAGMENT).toString(),movieDetails)
       }else {
           val mFragment = MovieListScreenFragment.newInstance()
           openFragment(mFragment)
       }


    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.layout_container_home, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun getFramentClass(mFragmentName: String,movieDetails: MovieDetails){
        var mFragment :Fragment? = null
        when(mFragmentName){
            Constants.MOVE_LIST_FRAGMENTNAME->{
                 mFragment = MovieListScreenFragment.newInstance()
            }
            Constants.MOVE_DETAIL_FRAGMENTNAME ->{

                assert(
                    supportActionBar != null //null check
                )
                supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#9f000000")))
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                toolbar_title.text=""
                mFragment = MovieDetailScreenFragment.newInstance(movieDetails)
            }
        }

        openFragment(mFragment!!)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                this.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
