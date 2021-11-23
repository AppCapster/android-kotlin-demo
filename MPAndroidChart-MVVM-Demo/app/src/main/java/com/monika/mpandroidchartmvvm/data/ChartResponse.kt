package com.monika.mpandroidchartmvvm.data

import com.monika.mpandroidchartmvvm.model.ChartModel

/**
 * @author Monika
 */
data class ChartResponse(val status: Int?, val msg: String?, val data: List<ChartModel>?) {
    fun isSuccess(): Boolean = (status == 200)
}