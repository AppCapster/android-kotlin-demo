package com.monika.mpandroidchartmvvm.view

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.view.View
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate.rgb
import com.monika.mpandroidchartmvvm.R
import com.monika.mpandroidchartmvvm.model.BarChartModel
import com.monika.mpandroidchartmvvm.model.BarChartWrapperModel
import com.monika.mpandroidchartmvvm.model.ChartModel
import com.monika.mpandroidchartmvvm.mpcharts.BarChartWrapper
import java.util.*
import kotlin.collections.ArrayList

class ChartViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val barChart: BarChart = view.findViewById(R.id.barChart)
    private val btnOrientation: ImageButton = view.findViewById(R.id.btnOrientation)
    private val btnShare: ImageButton = view.findViewById(R.id.btnShare)
    private val view = view
    private var isPortrait = false

    private val dataSetColors = ArrayList<Int>()

    fun bind(chartModel: ChartModel, listener: (ChartModel) -> Unit) {

        setClickListener(listener, chartModel)

        dataSetColors.add(ContextCompat.getColor(view.context, R.color.barChartMarkerViewLabel1TextColor))
        dataSetColors.add(ContextCompat.getColor(view.context, R.color.barChartMarkerViewLabel2TextColor))

        dataSetColors.add(rgb("#2471A3"))
        dataSetColors.add(rgb("#1ABC9C"))
        dataSetColors.add(rgb("#27AE60"))
        dataSetColors.add(rgb("#F1C40F"))
        dataSetColors.add(rgb("#F39C12"))
        dataSetColors.add(rgb("#E67E22"))
        dataSetColors.add(rgb("#D35400"))
        dataSetColors.add(rgb("#2E4053"))
        dataSetColors.add(rgb("#145A32"))
        dataSetColors.add(rgb("#0B5345"))

        if (chartModel.chartData is BarChartWrapperModel) {
            when (chartModel.chartType) {
                "NegativeBar" -> {
                    setMultiBarNegativeData(
                        chartModel.chartType,
                        chartModel.chartData.xAxisVal,
                        chartModel.chartData.yAxisVal, view.context
                    )
                }
                "MultiBar" -> {
                    setMultiBarData(
                        chartModel.chartType,
                        chartModel.chartData.xAxisVal,
                        chartModel.chartData.yAxisVal
                    )
                }
                "Bar" -> {
                    setBarData(
                        chartModel.chartType,
                        chartModel.chartData.xAxisVal,
                        chartModel.chartData.yAxisVal
                    )
                }
                "StackBar" -> {
                    setStackBarData(
                        chartModel.chartType,
                        chartModel.chartData.xAxisVal,
                        chartModel.chartData.yAxisVal
                    )
                }
            }
        }

        btnOrientation.setOnClickListener {
            val activity = view.context as Activity
            if (isPortrait) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            } else {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            isPortrait = !isPortrait
        }
    }

    private fun setClickListener(
        listener: (ChartModel) -> Unit,
        chartModel: ChartModel
    ) {
        itemView.setOnClickListener {
            listener(chartModel)
        }
    }

    private fun setMultiBarNegativeData(
        chartTitle: String,
        xAxisVal: Array<String>,
        yAxisVal: LinkedHashMap<String, LinkedHashMap<String, FloatArray>>,
        context: Context
    ) {
        val multiBarChart =
            BarChartWrapper().multiBarNegativeChart(
                barChart,
                xAxisVal,
                yAxisVal,
                dataSetColors,
                chartTitle,
                context
            )
        multiBarChart.invalidate()
    }

    private fun setMultiBarData(
        chartTitle: String,
        xAxisVal: Array<String>,
        yAxisVal: LinkedHashMap<String, LinkedHashMap<String, FloatArray>>
    ) {
        val multiBarChart =
            BarChartWrapper().multiBarChart(
                barChart,
                xAxisVal,
                yAxisVal, dataSetColors,
                chartTitle
            )
        multiBarChart.invalidate()
    }

    private fun setBarData(
        chartTitle: String,
        xAxisVal: Array<String>,
        yAxisVal: LinkedHashMap<String, LinkedHashMap<String, FloatArray>>
    ) {
        val multiBarChart =
            BarChartWrapper().barChart(
                barChart,
                xAxisVal,
                yAxisVal, dataSetColors,
                chartTitle
            )
        multiBarChart.invalidate()
    }

    private fun setStackBarData(
        chartTitle: String,
        xAxisVal: Array<String>,
        yAxisVal: LinkedHashMap<String, LinkedHashMap<String, FloatArray>>
    ) {
        val multiBarChart =
            BarChartWrapper().barStackChart(
                barChart,
                xAxisVal,
                yAxisVal, dataSetColors,
                chartTitle
            )
        multiBarChart.invalidate()
    }
}