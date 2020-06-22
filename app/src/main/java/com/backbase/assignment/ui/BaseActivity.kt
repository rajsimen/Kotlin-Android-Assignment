package com.backbase.assignment.ui

import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.backbase.assignment.R

import androidx.coordinatorlayout.widget.CoordinatorLayout


abstract class BaseActivity :AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var  coordinatorLayout : CoordinatorLayout

    override fun setContentView(layoutResID: Int) {
        coordinatorLayout = layoutInflater.inflate(R.layout.layout_base, null) as CoordinatorLayout
        var activityContainer: FrameLayout = coordinatorLayout.findViewById(R.id.layout_container)


        toolbar = coordinatorLayout.findViewById(R.id.toolbar)
        toolbar.title=""
        setSupportActionBar(toolbar)
        layoutInflater.inflate(layoutResID, activityContainer, true)

        super.setContentView(coordinatorLayout)
    }

    fun setScreenTitle(title: String) {
//        mTextViewScreenTitle.text = title
        toolbar.title = title
    }
}