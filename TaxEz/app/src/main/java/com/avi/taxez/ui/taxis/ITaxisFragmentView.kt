package com.avi.taxez.ui.taxis

import com.avi.taxez.base.fragment.IBaseFragmentView
import com.avi.taxez.data.models.Taxi

/**
 * Created by avi.barel on 27/07/2018.
 */
interface ITaxisFragmentView : IBaseFragmentView {

    interface TaxisFragmentCallback {
        fun onTaxiClicked(taxi: Taxi)
    }

    fun setListener(listener: TaxisFragmentCallback?)
    fun toggleProgress(isLoading: Boolean)
    fun updateSearchTerm(origin: String, destination: String)
    fun updateTaxisResult(taxis: ArrayList<Taxi>)

}