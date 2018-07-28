package com.avi.taxez.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by avi.barel on 27/07/2018.
 */
data class Taxi(
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("stationName") val stationName: String?,
    @SerializedName("etaInMinutes") val etaInMinutes: Int?
)