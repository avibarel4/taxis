package com.avi.taxez.ui.taxis

import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.avi.taxez.R
import com.avi.taxez.base.fragment.BaseFragmentViewImpl
import com.avi.taxez.data.models.Taxi

/**
 * Created by avi.barel on 27/07/2018.
 */
class TaxisFragmentViewImpl(layoutInflater: LayoutInflater, container: ViewGroup?, val savedState: Bundle?) : BaseFragmentViewImpl(layoutInflater, container), ITaxisFragmentView, AvailableTaxisAdapter.TaxisAdapterCallback {

    private var txtSearchTerm: TextView? = null
    private var progress: ProgressBar? = null
    private var recyclerTaxis: RecyclerView? = null
    private var taxisAdapter: AvailableTaxisAdapter? = null

    private var listener: ITaxisFragmentView.TaxisFragmentCallback? = null

    override fun layoutResId(): Int {
        return R.layout.fragment_taxis
    }

    override fun getViewState(): Bundle? {
        return null
    }

    override fun initViews(rootView: View) {
        txtSearchTerm = rootView.findViewById(R.id.text_search_term)
        progress = rootView.findViewById(R.id.progress)

        recyclerTaxis = rootView.findViewById(R.id.recycler_taxis)
        taxisAdapter = AvailableTaxisAdapter()

        recyclerTaxis?.layoutManager = LinearLayoutManager(rootView.context, RecyclerView.VERTICAL, false)
        recyclerTaxis?.adapter = taxisAdapter

        taxisAdapter?.setListener(this)
    }

    override fun releaseViews() {

        taxisAdapter?.setListener(null)

        txtSearchTerm = null
        progress = null

        recyclerTaxis?.adapter = null
        recyclerTaxis?.layoutManager = null

        recyclerTaxis = null
        taxisAdapter = null
    }

    override fun setListener(listener: ITaxisFragmentView.TaxisFragmentCallback?) {
        this.listener = listener
    }

    override fun toggleProgress(isLoading: Boolean) {
        if (isLoading) {
            recyclerTaxis?.visibility = View.GONE
            progress?.visibility = View.VISIBLE
        } else {
            progress?.visibility = View.GONE
            recyclerTaxis?.visibility = View.VISIBLE
        }
    }

    override fun updateSearchTerm(origin: String, destination: String) {
        val title = getRootView().resources.getString(R.string.taxis_result_title, origin, destination)
        txtSearchTerm?.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(title)
        }
    }

    override fun updateTaxisResult(taxis: ArrayList<Taxi>) {
        taxisAdapter?.setData(taxis)
    }

    override fun onTaxiClicked(taxi: Taxi) {
        listener?.onTaxiClicked(taxi)
    }

}