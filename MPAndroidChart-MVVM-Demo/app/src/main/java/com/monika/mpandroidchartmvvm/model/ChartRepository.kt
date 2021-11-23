package com.monika.mpandroidchartmvvm.model

import com.monika.mpandroidchartmvvm.data.OperationCallback

/**
 * @author Monika
 */
class ChartRepository(private val chartDataSource: ChartDataSource) {

    fun fetchCharts(callback: OperationCallback<ChartModel>) {
        chartDataSource.retrieveCharts(callback)
    }

    fun cancel() {
        chartDataSource.cancel()
    }
}