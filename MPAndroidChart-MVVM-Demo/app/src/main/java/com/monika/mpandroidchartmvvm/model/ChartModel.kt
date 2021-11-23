package com.monika.mpandroidchartmvvm.model

import java.io.Serializable

/**
 * @author : Monika
 */
data class ChartModel(val chartType: String, val chartData: List<BarChartModel>) : Serializable
data class BarChartModel(val xValue: Float, val yValue: Float, val xAxisValue: String) :
    Serializable

