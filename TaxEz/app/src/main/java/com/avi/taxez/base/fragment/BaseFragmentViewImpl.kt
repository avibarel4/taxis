package com.avi.taxez.base.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by avi.barel on 27/07/2018.
 */
abstract class BaseFragmentViewImpl(layoutInflater: LayoutInflater, container: ViewGroup?) : IBaseFragmentView {

    private var rootView: View

    init {
        if (layoutInflater == null) {
            throw RuntimeException("One must provide Layout Inflater")
        }

        rootView = layoutInflater.inflate(this.layoutResId(), container, false)
    }

    final override fun getRootView(): View {
        return rootView
    }

}