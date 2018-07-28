package com.avi.taxez.data

import com.avi.taxez.data.models.Taxi
import com.avi.taxez.data.models.TaxisResponse
import com.ladbrokescoral.lcgcore.utils.tests.MockUtils
import io.reactivex.Single

/**
 * Created by avi.barel on 27/07/2018.
 */
class TaxiService private constructor() {

    private var counter = 0

    companion object {
        val INSTANCE by lazy { TaxiService() }
    }

    fun getAvailableTaxis(origin: String, destination: String): Single<ArrayList<Taxi>> {

        val file = if (counter % 3 == 0) {
            "mock/taxis_3.json"
        } else if (counter % 2 == 0) {
            "mock/taxis_2.json"
        } else {
            "mock/taxis_1.json"
        }

        counter++

        return Single.fromCallable {

            // simulate long task
            Thread.sleep(1000)

            val taxisResponse = MockUtils.loadJson<TaxisResponse>(file, TaxisResponse::class.java)
            taxisResponse.availableTaxis ?: arrayListOf()
        }
    }

}