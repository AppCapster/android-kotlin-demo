package com.monika.mpandroidchartmvvm.mpcharts

import android.content.Context
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.monika.mpandroidchartmvvm.R
import com.monika.mpandroidchartmvvm.view.custom.BarChartMarkerView
import com.monika.mpandroidchartmvvm.view.custom.RoundBarChartRender
import java.util.*
import kotlin.collections.HashMap


class BarChartWrapper {
    private var colors = ArrayList<Int>()

    fun barChart(
        barChart: BarChart,
        xAxisVal: Array<String>,
        yAxisVal: LinkedHashMap<String, LinkedHashMap<String, FloatArray>>,
        dataSetColors: ArrayList<Int>,
        chatTitle: String
    ): BarChart {

        //set color
        addColorTemplate(dataSetColors)

        //set data
        val key = yAxisVal.keys.iterator().next()
        val data = yAxisVal[key]?.let { createChartData(it) }

        //set xAxis
        xAxisConfigSingleBar(barChart, xAxisVal)

        barChart.data = data
        barChart.setFitBars(true) // make the x-axis fit exactly all bars

        // set bar label
        legend(barChart)

        return barChart
    }

    fun barStackChart(
        barChart: BarChart,
        xAxisVal: Array<String>,
        yAxisVal: LinkedHashMap<String, LinkedHashMap<String, FloatArray>>,
        dataSetColors: ArrayList<Int>,
        chartTitle: String
    ): BarChart {

        //set color
        addColorTemplate(dataSetColors)

        //set data
        val (barData, labelHm) = createStackBarChartData(yAxisVal)
        configureMultiBarChartAppearance(barChart, chartTitle)

        //set xAxis
        xAxisConfiguration(barChart, xAxisVal)

        // set bar label
        val legend = legend(barChart)
        legendEntry(legend, labelHm)

        barData.setValueFormatter(LargeValueFormatter())
        barChart.data = barData
//        barChart.barData.barWidth = barWidth

        barChart.data.isHighlightEnabled = true

        val groupSize = yAxisVal.size
        val barSpace = 0.01f
        val barWidth = 0.9f / groupSize // x groupSize dataSet
        barChart.barData.barWidth = barWidth
        val groupEvaluatedSpace = 1f - (barSpace + barWidth) * groupSize
        barChart.groupBars(0f, groupEvaluatedSpace, barSpace)

        barChart.data = barData

        return barChart
    }

    fun multiBarChart(
        barChart: BarChart,
        xAxisVal: Array<String>,
        yAxisVal: LinkedHashMap<String, LinkedHashMap<String, FloatArray>>,
        dataSetColors: ArrayList<Int>,
        chatTitle: String
    ): BarChart {

        //set color
        addColorTemplate(dataSetColors)

        //set data
        configureMultiBarChartAppearance(barChart, chatTitle)

        //set xAxis
        xAxisConfiguration(barChart, xAxisVal)

        // set bar label
        legend(barChart)

        val key = yAxisVal.keys.iterator().next()
        val yValues = yAxisVal[key]
        if (yValues != null) {
            val data = createChartData(yValues)
            return prepareChartData(data, barChart, yValues)
        }

        return barChart
    }

    fun multiBarNegativeChart(
        barChart: BarChart,
        xAxisVal: Array<String>,
        yAxisVal: LinkedHashMap<String, LinkedHashMap<String, FloatArray>>,
        dataSetColors: ArrayList<Int>,
        chatTitle: String,
        context: Context
    ): BarChart {

        //set color
        addColorTemplate(dataSetColors)

        //set data
        configureMultiBarNegativeChartAppearance(barChart)

        //set Axis
        xAxisConfiguration(barChart, xAxisVal)

        // set bar label
        legend(barChart)

        //set views
        setMarkerView(barChart, context)
        setCustomRoundedBar(barChart)

        val key = yAxisVal.keys.iterator().next()
        val yValues = yAxisVal[key]
        if (yValues != null) {
            val data = createChartData(yValues)
            return prepareChartData(data, barChart, yValues)
        }

        return barChart
    }

    /* */
    private fun configureMultiBarChartAppearance(
        chart: BarChart,
        chatTitle: String
    ) {
        chart.setPinchZoom(true)
        chart.description.text = chatTitle
        chart.axisRight.isEnabled = false
    }

    private fun configureMultiBarNegativeChartAppearance(
        chart: BarChart
    ) {
        chart.setPinchZoom(true)
        chart.setDrawGridBackground(false)

        chart.description.isEnabled = false
        chart.axisRight.isEnabled = false
        chart.extraBottomOffset = 10f
    }

    private fun prepareChartData(
        data: BarData?,
        chart: BarChart,
        yAxisVal: LinkedHashMap<String, FloatArray>
    ): BarChart {
        val groupSize = yAxisVal.size
        val barSpace = 0.03f
        val barWidth = 0.5f / groupSize // x groupSize dataSet

        chart.data = data
        chart.barData.barWidth = barWidth

        val groupEvaluatedSpace = 1f - (barSpace + barWidth) * groupSize
        chart.groupBars(0f, groupEvaluatedSpace, barSpace)

        return chart
    }

    private fun createChartData(
        yValue: LinkedHashMap<String, FloatArray>
    ): BarData {
        var colorCount = 0
        val dataSets: ArrayList<IBarDataSet> = ArrayList()
        for (yLabelKey in yValue.keys) {

            val yEntry = ArrayList<BarEntry>()
            val floatyValue: FloatArray? = yValue[yLabelKey]

            if (floatyValue != null && floatyValue.isNotEmpty()) {
                for (j in floatyValue.indices) {
                    yEntry.add(BarEntry(j.toFloat(), floatyValue[j]))
                }
                val set = BarDataSet(yEntry, yLabelKey)
                set.setColors(colors[colorCount])
                set.setDrawValues(false)
                //Reset count and increment Count
                colorCount = colorCountOperation(colorCount)
                dataSets.add(set)
            }
        }
        return BarData(dataSets)
    }

    private fun createStackBarChartData(
        barDateSetHM: LinkedHashMap<String, LinkedHashMap<String, FloatArray>>
    ): Pair<BarData, HashMap<String, Int>> {

        val barDataSetSize = barDateSetHM.size
        println("barDataSetSize = $barDataSetSize")
        val barDateSetKeyArray = barDateSetHM.keys.toTypedArray()
        val ySeriesFloatKeyArray = barDateSetHM[barDateSetKeyArray[0]]!!.keys.toTypedArray()
        val categorySeriesSize = barDateSetHM[barDateSetKeyArray[0]]?.keys!!.size
        println("categorySeriesSize = $categorySeriesSize")
        val ySeriesFloatArraySize =
            barDateSetHM[barDateSetKeyArray[0]]!![ySeriesFloatKeyArray[0]]!!.size
        println("ySeriesFloatArraySize = $ySeriesFloatArraySize")
        val dataSets: ArrayList<IBarDataSet> = ArrayList()
        val legendLabelHm: HashMap<String, Int> = HashMap()
        var label: String? = null
        for (barDataSetIndex in 0 until barDataSetSize) {
            val yEntry = ArrayList<BarEntry>()
            val labelHm: HashMap<String, Int> = HashMap()
            for (ySeriesIndex in 0 until ySeriesFloatArraySize) {
                val yBarEntryFloatArray = FloatArray(categorySeriesSize)
                println("**Init Size yBarEntryArray = $categorySeriesSize")
                var colorCount = 0
                for (categoryIndex in 0 until categorySeriesSize) {
                    val yBarDateSetValues = barDateSetHM[barDateSetKeyArray[barDataSetIndex]]
                    println("**yBarDateSetValues = ${yBarDateSetValues.toString()}")

                    val ySeriesValue =
                        yBarDateSetValues!![ySeriesFloatKeyArray[categoryIndex]]?.get(ySeriesIndex)
                    println("**yBarDateSetValues = ${ySeriesValue.toString()}")

                    label = ySeriesFloatKeyArray[categoryIndex]
                    println("label = $label")
                    labelHm[label] = colors[colorCount]
                    //Reset count and increment Count
                    colorCount = colorCountOperation(categoryIndex)

                    yBarEntryFloatArray[categoryIndex] = ySeriesValue!!
                    println("**Add yBarEntryFloatArray $categoryIndex = $ySeriesValue")
                    println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Added BarEntryFloatArray $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$")
                }
                println("**Size yBarEntryArray = ${yBarEntryFloatArray.size}")

                yEntry.add(BarEntry(ySeriesIndex.toFloat(), yBarEntryFloatArray))
                println("**Added ArrayList<BarEntry> X= ${ySeriesIndex.toFloat()},Y= yBarEntryFloatArray $yBarEntryFloatArray")
                println("**************************Added*****BarEntry**********************************")
                //        Prepare Group Data

            }
            val dataSet = BarDataSet(yEntry, barDateSetKeyArray[barDataSetIndex])
            println("**Added label X= ${label},Y= ArrayList<BarEntry> $yEntry")
            println("**Size label ${labelHm.values.toList().size}")
            dataSet.colors = labelHm.values.toList()
            dataSet.label = barDateSetKeyArray[barDataSetIndex]
            legendLabelHm.putAll(labelHm)
            dataSet.setDrawIcons(false)

            println("**Added ArrayList<IBarDataSet> $dataSet")
            dataSets.add(dataSet)
            println("**Size ArrayList<IBarDataSet> ${dataSets.size}")
        }

        /*    // Define and Prepare Y-Axis (Stacked Data)
            val yValueGroup1 = ArrayList<BarEntry>()
            val yValueGroup2 = ArrayList<BarEntry>()
            // Draw the graph
            val barDataSet1: BarDataSet
            val barDataSet2: BarDataSet
            yValueGroup1.add(BarEntry(1f, floatArrayOf(9.toFloat(), 3.toFloat())))
            yValueGroup2.add(BarEntry(1f, floatArrayOf(2.toFloat(), 7.toFloat())))
            yValueGroup1.add(BarEntry(2f, floatArrayOf(3.toFloat(), 3.toFloat())))
            yValueGroup2.add(BarEntry(2f, floatArrayOf(4.toFloat(), 8.toFloat())))
            yValueGroup1.add(BarEntry(3f, floatArrayOf(3.toFloat(), 3.toFloat())))
            yValueGroup2.add(BarEntry(3f, floatArrayOf(4.toFloat(), 2.toFloat())))
            yValueGroup1.add(BarEntry(4f, floatArrayOf(3.toFloat(), 3.toFloat())))
            yValueGroup2.add(BarEntry(4f, floatArrayOf(4.toFloat(), 5.toFloat())))
            yValueGroup1.add(BarEntry(5f, floatArrayOf(9.toFloat(), 3.toFloat())))
            yValueGroup2.add(BarEntry(5f, floatArrayOf(10.toFloat(), 6.toFloat())))
            yValueGroup1.add(BarEntry(6f, floatArrayOf(11.toFloat(), 1.toFloat())))
            yValueGroup2.add(BarEntry(6f, floatArrayOf(12.toFloat(), 2.toFloat())))
            yValueGroup1.add(BarEntry(7f, floatArrayOf(11.toFloat(), 7.toFloat())))
            yValueGroup2.add(BarEntry(7f, floatArrayOf(12.toFloat(), 5.toFloat())))
            yValueGroup1.add(BarEntry(8f, floatArrayOf(11.toFloat(), 9.toFloat())))
            yValueGroup2.add(BarEntry(8f, floatArrayOf(12.toFloat(), 8.toFloat())))
            yValueGroup1.add(BarEntry(9f, floatArrayOf(11.toFloat(), 3.toFloat())))
            yValueGroup2.add(BarEntry(9f, floatArrayOf(12.toFloat(), 2.toFloat())))
            yValueGroup1.add(BarEntry(10f, floatArrayOf(11.toFloat(), 2.toFloat())))
            yValueGroup2.add(BarEntry(10f, floatArrayOf(12.toFloat(), 7.toFloat())))
            yValueGroup1.add(BarEntry(11f, floatArrayOf(11.toFloat(), 6.toFloat())))
            yValueGroup2.add(BarEntry(11f, floatArrayOf(12.toFloat(), 5.toFloat())))
            yValueGroup1.add(BarEntry(12f, floatArrayOf(11.toFloat(), 2.toFloat())))
            yValueGroup2.add(BarEntry(12f, floatArrayOf(12.toFloat(), 3.toFloat())))
            //        Prepare Group Data
            barDataSet1 = BarDataSet(yValueGroup1, "")
            barDataSet1.setColors(
                ColorTemplate.MATERIAL_COLORS[0],
                ColorTemplate.MATERIAL_COLORS[1]
            )
            barDataSet1.label = "2016"
            barDataSet1.setDrawIcons(false)
            barDataSet2 = BarDataSet(yValueGroup2, "")
            barDataSet2.label = "2017"
            barDataSet2.setColors(
                ColorTemplate.MATERIAL_COLORS[0],
                ColorTemplate.MATERIAL_COLORS[1]
            )
            barDataSet2.setDrawIcons(false)*/
        return Pair(BarData(dataSets), legendLabelHm)
    }

    private fun legend(barChart: BarChart): Legend {
        val legend = barChart.legend

        legend.isEnabled = true
        barChart.animateX(2000, Easing.EaseInOutQuart)
        barChart.animateY(2000, Easing.EaseInOutQuart)

        return legend
    }

    private fun legendEntry(legend: Legend, labelHm: HashMap<String, Int>) {
        //        Customize Bar Chart Legends using Legend Entry
        val legendEntries = arrayListOf<LegendEntry>()
        for ((key, value) in labelHm) {
            legendEntries.add(
                LegendEntry(
                    key,
                    Legend.LegendForm.SQUARE,
                    8f,
                    8f,
                    null,
                    value
                )
            )
        }
        legend.setCustom(legendEntries)
    }

    private fun xAxisConfigSingleBar(barChart: BarChart, xAxisVal: Array<String>) {
        val xAxis = barChart.xAxis

        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true

        xAxis.textSize = 9f
        xAxis.labelCount = 12

        if (xAxisVal.size > 12)
            xAxis.labelCount = xAxisVal.size / 3

        xAxis.mAxisMaximum = 12f
        xAxis.position = XAxis.XAxisPosition.TOP
        xAxis.valueFormatter = IndexAxisValueFormatter(xAxisVal)
    }

    private fun xAxisConfiguration(chart: BarChart, xAxisVal: Array<String>) {
        val xAxis = chart.xAxis

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(xAxisVal)
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true

        xAxis.setCenterAxisLabels(true)
        xAxis.setDrawGridLines(false)
        xAxis.setAvoidFirstLastClipping(true)

        xAxis.labelCount = xAxisVal.size

        xAxis.axisMinimum = 0f
        xAxis.mAxisMaximum = xAxisVal.size.toFloat()
    }

    private fun addColorTemplate(colors: ArrayList<Int>) {
        if (this.colors.size == 0) {
            this.colors = colors
        }
    }

    private fun colorCountOperation(colorCount: Int): Int {
        var colorCount1 = colorCount
        if (colorCount1 == colors.size) {
            colorCount1 = 0
        } else {
            colorCount1++
        }
        return colorCount1
    }

    private fun setMarkerView(chart: BarChart, context: Context) {
        val mv = BarChartMarkerView(context, R.layout.barchart_marker_view)
        mv.chartView = chart // For bounds control
        chart.marker = mv // Set the marker to the chart
    }

    private fun setCustomRoundedBar(chart: BarChart) {
        val barChartRender =
            RoundBarChartRender(chart, chart.animator, chart.viewPortHandler)
        barChartRender.setRadius(15)
        chart.renderer = barChartRender
    }
}