package com.avi.taxez.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.avi.taxez.base.fragment.BaseFragment
import com.avi.taxez.ui.main.MainActivity

/**
 * Created by avi.barel on 27/07/2018.
 */
class SearchFragment : BaseFragment<SearchFragmentViewModel, SearchFragmentViewImpl>(), ISearchFragmentView.SearchFragmentCallbacks {

    override fun handleBackPressed(): Boolean {
        return false
    }

    override fun getFragmentTag(): String {
        return SearchFragment::class.java.simpleName
    }

    override fun provideFragmentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): SearchFragmentViewImpl {
        return SearchFragmentViewImpl(inflater, container, savedInstanceState)
    }

    override fun provideViewModel(): Class<SearchFragmentViewModel> {
        return SearchFragmentViewModel::class.java
    }

    override fun onSearchTaxiSubmit(origin: String, destination: String) {
        (activity as? MainActivity)?.let {
            it.openTaxisList(origin, destination)
        }
    }

    override fun onResume() {
        super.onResume()

        viewImpl.setListener(this)
    }

    override fun onPause() {
        super.onPause()

        viewImpl.setListener(null)
    }

}