package com.monika.mpandroidchartmvvm.model

import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author : Monika
 */
data class ChartModel(val chartType: String, val chartData: Any) : Serializable
data class BarChartModel(val xValue: Float, val yValue: Float, val xAxisValue: String) :
    Serializable

data class BarChartWrapperModel(
    val chartType: String,
    val chartTitle: String,
    val xAxisVal: Array<String>,
    val yAxisVal: LinkedHashMap<String, LinkedHashMap<String, FloatArray>>
)

data class LineChartWrapperModel(
    val chartType: String,
    val chartTitle: String,
    val xAxisVal: Array<String>,
    val yAxisVal: LinkedHashMap<String, LinkedHashMap<String, FloatArray>>
) : Serializable
