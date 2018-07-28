package com.avi.taxez.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by avi.barel on 27/07/2018.
 */
data class TaxisResponse(
    @SerializedName("availableTaxis") val availableTaxis: ArrayList<Taxi>?
)