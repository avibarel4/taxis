package com.avi.taxez.ui.taxis

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.avi.taxez.R
import com.avi.taxez.base.App
import com.avi.taxez.data.models.Taxi
import com.bumptech.glide.Glide

/**
 * Created by avi.barel on 27/07/2018.
 */
class AvailableTaxisAdapter : RecyclerView.Adapter<AvailableTaxisAdapter.ViewHolder>(), View.OnClickListener {

    companion object {
        private val FORMAT_MINUTES = App.context.getString(R.string.taxi_eta_minutes)
        private val FORMAT_HOURS_MINUTES = App.context.getString(R.string.taxi_eta_hours_minutes)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgStation: ImageView = view.findViewById(R.id.image_station)
        val txtStation: TextView = view.findViewById(R.id.text_station)
        val txtETA: TextView = view.findViewById(R.id.text_eta)
    }

    private var items: ArrayList<Taxi> = arrayListOf()

    private var listener: TaxisAdapterCallback? = null

    fun setData(taxis: ArrayList<Taxi>) {
        val diffResult = DiffUtil.calculateDiff(TaxisDiffCallback(items, taxis))
        items.clear()
        items.addAll(taxis)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setListener(listener: TaxisAdapterCallback?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_available_taxi, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.tag = position

        val taxi = items[position]

        holder.imgStation.let {
            Glide.with(it.context)
                .load(taxi.imageUrl)
                .into(it)
        }

        holder.txtStation.text = taxi.stationName
        holder.txtETA.let {
            it.text = getEtaText(taxi.etaInMinutes ?: 0)
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)

        holder.itemView.setOnClickListener(this)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)

        holder.itemView.setOnClickListener(null)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun getEtaText(etaInMinutes: Int): String {

        if (etaInMinutes == 0) {
            return ""
        }

        val hours = etaInMinutes / 60 //since both are ints, you get an int
        val minutes = etaInMinutes % 60

        return if (hours > 0) {
            String.format(FORMAT_HOURS_MINUTES, hours, minutes)
        } else {
            String.format(FORMAT_MINUTES, minutes)
        }
    }

    override fun onClick(v: View) {
        if (!App.isTouchEnabled()) {
            return
        }

        App.disableTouchEventForDefaultDuration()

        (v.tag as? Int)?.let {
            if (it in 0..(itemCount - 1)) {
                listener?.onTaxiClicked(items[it])
            }
        }
    }

    interface TaxisAdapterCallback {
        fun onTaxiClicked(taxi: Taxi)
    }

}