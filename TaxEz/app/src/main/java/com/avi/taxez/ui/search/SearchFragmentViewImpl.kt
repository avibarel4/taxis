package com.avi.taxez.ui.search

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.avi.taxez.R
import com.avi.taxez.base.App
import com.avi.taxez.base.fragment.BaseFragmentViewImpl

/**
 * Created by avi.barel on 27/07/2018.
 */
class SearchFragmentViewImpl(layoutInflater: LayoutInflater, container: ViewGroup?, val savedState: Bundle?) : BaseFragmentViewImpl(layoutInflater, container), ISearchFragmentView, View.OnClickListener, View.OnFocusChangeListener {

    private var editOrigin: EditText? = null
    private var editDestination: EditText? = null
    private var btnSearch: View? = null

    private var listener: ISearchFragmentView.SearchFragmentCallbacks? = null

    override fun layoutResId(): Int {
        return R.layout.fragment_search
    }

    override fun getViewState(): Bundle? {
        return null
    }

    override fun initViews(rootView: View) {
        editOrigin = rootView.findViewById(R.id.edit_origin)
        editDestination = rootView.findViewById(R.id.edit_destination)
        btnSearch = rootView.findViewById(R.id.btn_search)

        btnSearch?.setOnClickListener(this)

        editOrigin?.setOnFocusChangeListener(this)
        editDestination?.setOnFocusChangeListener(this)
    }

    override fun releaseViews() {

        editOrigin?.setOnFocusChangeListener(null)
        editDestination?.setOnFocusChangeListener(null)

        btnSearch?.setOnClickListener(null)

        editOrigin = null
        editDestination = null
        btnSearch = null
    }

    override fun setListener(listener: ISearchFragmentView.SearchFragmentCallbacks?) {
        this.listener = listener
    }

    override fun onClick(v: View) {
        if (!App.isTouchEnabled()) {
            return
        }

        App.disableTouchEventForDefaultDuration()

        when (v.id) {
            R.id.btn_search -> {
                val origin = editOrigin?.text?.toString() ?: ""
                val destination = editDestination?.text?.toString() ?: ""

                var isValid = isValidInput(editOrigin) && isValidInput(editDestination)

                if (isValid) {
                    listener?.onSearchTaxiSubmit(origin, destination)
                }
            }
        }

    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (!hasFocus) {
            when (v.id) {
                R.id.edit_origin,
                R.id.edit_destination -> {
                    isValidInput(v as? EditText)
                }
            }
        }
    }

    private fun isValidInput(editText: EditText?): Boolean {
        editText?.let {
            val errorText = when (editText.id) {
                R.id.edit_origin -> {
                    "Please enter Pickup Location"
                }
                R.id.edit_destination -> {
                    "Please enter Your Destination"
                }
                else -> {
                    null
                }
            }

            if (TextUtils.isEmpty(it.text)) {
                it.error = errorText
                return false
            } else {
                it.error = null
            }

        }

        return true
    }
}