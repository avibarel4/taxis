package com.avi.taxez.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.avi.taxez.R
import com.avi.taxez.base.activity.BaseActivityViewImpl
import com.avi.taxez.base.fragment.BaseFragment
import com.avi.taxez.ui.search.SearchFragment

/**
 * Created by avi.barel on 27/07/2018.
 */
class MainActivityViewImpl(context: Context, container: ViewGroup?) : BaseActivityViewImpl(context, container), IMainActivityView {

    override fun layoutResId(): Int {
        return R.layout.activity_main
    }

    override fun getViewState(): Bundle? {
        return null
    }

    override fun initialFragment(): BaseFragment<*, *>? {
        return SearchFragment()
    }

    override fun containerId(): Int {
        return R.id.container
    }

    override fun initViews(rootView: View) {

    }

    override fun releaseViews() {

    }

}