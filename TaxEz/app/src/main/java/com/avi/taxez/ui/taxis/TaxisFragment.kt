package com.avi.taxez.ui.taxis

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.avi.taxez.base.fragment.BaseFragment
import com.avi.taxez.data.models.Taxi

/**
 * Created by avi.barel on 27/07/2018.
 */
class TaxisFragment : BaseFragment<TaxisFragmentViewModel, TaxisFragmentViewImpl>() {

    companion object {

        private const val EXTRA_KEY_ORIGIN = "extra_key_origin"
        private const val EXTRA_KEY_DESTINATION = "extra_key_destination"

        fun newInstance(origin: String, destination: String): TaxisFragment {
            val fragment = TaxisFragment()
            val args = Bundle()
            args.putString(EXTRA_KEY_ORIGIN, origin)
            args.putString(EXTRA_KEY_DESTINATION, destination)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (savedInstanceState == null) {
                val origin = it.getString(EXTRA_KEY_ORIGIN, "")
                val destination = it.getString(EXTRA_KEY_DESTINATION, "")
                viewModel.searchTaxis(origin, destination)
            }
        }

        viewModel.taxisLiveData.observe(this, Observer {
            it?.let {
                viewImpl.updateTaxisResult(it)
            }
        })

        viewModel.taxisErrorLiveData.observe(this, Observer {
            Toast.makeText(activity, "Error: $it", Toast.LENGTH_LONG).show()
        })

        viewModel.isLoading.observe(this, Observer {
            viewImpl.toggleProgress(it == true)
        })
    }

    override fun onResume() {
        super.onResume()

        viewImpl.updateSearchTerm(viewModel.searchTermOrigin, viewModel.searchTermDestination)
    }

    override fun handleBackPressed(): Boolean {
        return false
    }

    override fun getFragmentTag(): String {
        return TaxisFragment::class.java.simpleName
    }

    override fun provideFragmentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): TaxisFragmentViewImpl {
        return TaxisFragmentViewImpl(inflater, container, savedInstanceState)
    }

    override fun provideViewModel(): Class<TaxisFragmentViewModel> {
        return TaxisFragmentViewModel::class.java
    }

}