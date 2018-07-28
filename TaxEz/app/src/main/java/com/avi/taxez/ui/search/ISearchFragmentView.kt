package com.avi.taxez.ui.search

import com.avi.taxez.base.fragment.IBaseFragmentView

/**
 * Created by avi.barel on 27/07/2018.
 */
interface ISearchFragmentView : IBaseFragmentView {

    interface SearchFragmentCallbacks {
        fun onSearchTaxiSubmit(origin: String, destination: String)
    }

    fun setListener(listener: SearchFragmentCallbacks?)

}