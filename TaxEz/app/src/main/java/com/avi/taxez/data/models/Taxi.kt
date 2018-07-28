package com.avi.taxez.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by avi.barel on 27/07/2018.
 */
@Parcelize
data class Taxi(
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("stationName") val stationName: String?,
    @SerializedName("etaInMinutes") val etaInMinutes: Int?
) : Parcelable