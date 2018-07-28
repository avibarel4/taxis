package com.avi.taxez.ui.taxis

import android.support.v7.util.DiffUtil
import com.avi.taxez.data.models.Taxi

/**
 * Created by avi.barel on 28/07/2018.
 */
class TaxisDiffCallback(
    private val oldTaxis: ArrayList<Taxi>,
    private val newTaxis: ArrayList<Taxi>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldTaxis.size
    }

    override fun getNewListSize(): Int {
        return newTaxis.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTaxis[oldItemPosition] == newTaxis[newItemPosition] // TODO.. maybe some additional logic here??
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTaxis[oldItemPosition] == newTaxis[newItemPosition]
    }

}