package com.avi.taxez.ui.taxis

import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import android.util.Log
import com.avi.taxez.base.BaseViewModel
import com.avi.taxez.data.TaxiService
import com.avi.taxez.data.models.Taxi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by avi.barel on 27/07/2018.
 */
class TaxisFragmentViewModel : BaseViewModel() {

    private var refreshHandler: Handler?
    private var refreshRunnable: Runnable?

    var searchTermOrigin = ""
        private set
    var searchTermDestination = ""
        private set

    companion object {
        private val TAG = TaxisFragmentViewModel::class.java.simpleName

        private val REFRESH_INTERVAL = 5000L // 5 seconds
    }

    init {
        refreshHandler = Handler()
        refreshRunnable = Runnable {
            executeSearchQuery(false)
        }
    }

    override fun onCleared() {
        super.onCleared()

        refreshHandler?.removeCallbacks(refreshRunnable)
        refreshHandler = null
        refreshRunnable = null
    }

    val isLoading = MutableLiveData<Boolean>()
    val taxisLiveData = MutableLiveData<ArrayList<Taxi>>()
    val taxisErrorLiveData = MutableLiveData<String>()

    fun searchTaxis(origin: String, destination: String) {
        searchTermOrigin = origin
        searchTermDestination = destination

        executeSearchQuery(true)
    }

    private fun executeSearchQuery(newSearch: Boolean) {

        if (newSearch) {
            Log.d(TAG, "New Search for $searchTermOrigin - $searchTermDestination")
        } else {
            Log.d(TAG, "Refresh Results for $searchTermOrigin - $searchTermDestination")
        }

        // clear previous jobs
        compositeDisposable.clear()
        refreshHandler?.removeCallbacks(refreshRunnable)

        if (newSearch) { // display the Loading only for new searches
            isLoading.value = true
        }

        val disposable = TaxiService.INSTANCE.getAvailableTaxis(searchTermOrigin, searchTermDestination)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ taxis ->
                isLoading.value = false
                taxisLiveData.value = taxis

                // register for next refresh
                refreshHandler?.postDelayed(refreshRunnable, REFRESH_INTERVAL)

            },{
                isLoading.value = false
                taxisErrorLiveData.value = it.message
            })

        compositeDisposable.add(disposable)
    }

}