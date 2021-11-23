package com.monika.mpandroidchartmvvm.model

import com.monika.mpandroidchartmvvm.data.OperationCallback

/**
 * @author Monika
 */
interface ChartDataSource {

    fun retrieveCharts(callback: OperationCallback<ChartModel>)
    fun cancel()
}