package com.avi.taxez.ui.taxis

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.avi.taxez.R
import com.avi.taxez.data.models.Taxi
import com.bumptech.glide.Glide
import android.graphics.drawable.ColorDrawable
import android.view.Window

/**
 * Created by avi.barel on 28/07/2018.
 */
class TaxiDialogFragment : DialogFragment(), View.OnClickListener {

    private var layoutBackground: View? = null

    companion object {

        private const val EXTRA_KEY_TAXI = "extra_key_taxi"

        fun newInstance(taxi: Taxi): TaxiDialogFragment {
            val fragment = TaxiDialogFragment()
            val args = Bundle()
            args.putParcelable(EXTRA_KEY_TAXI, taxi)

            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)

        return dialog
    }

    override fun onStart() {
        super.onStart()

        val dialog = dialog
        if (dialog != null) {
            dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var taxi: Taxi? = null
        arguments?.let {
            taxi = it.getParcelable(EXTRA_KEY_TAXI)
        }

        val view = inflater.inflate(R.layout.dialog_fragment_taxi, container)

        layoutBackground = view.findViewById(R.id.layout_background)

        taxi?.let {
            val image = view.findViewById<ImageView>(R.id.image_station)
            Glide.with(image.context)
                .load(it.imageUrl)
                .centerCrop()
                .into(image)

            view.findViewById<TextView>(R.id.text_station).text = it.stationName
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        layoutBackground?.setOnClickListener(this)
    }

    override fun onPause() {
        super.onPause()

        layoutBackground?.setOnClickListener(null)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        layoutBackground = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.layout_background -> {
                dismissAllowingStateLoss()
            }
        }
    }

}