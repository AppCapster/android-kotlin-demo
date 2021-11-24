package com.monika.mpandroidchartmvvm.view

import android.graphics.Color
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate.rgb
import com.monika.mpandroidchartmvvm.R
import com.monika.mpandroidchartmvvm.model.BarChartModel
import com.monika.mpandroidchartmvvm.model.BarChartWrapperModel
import com.monika.mpandroidchartmvvm.model.ChartModel
import com.monika.mpandroidchartmvvm.mpcharts.BarChartWrapper
import com.monika.mpandroidchartmvvm.view.custom.BarChartMarkerView
import com.monika.mpandroidchartmvvm.view.custom.RoundBarChartRender
import java.util.*
import kotlin.collections.ArrayList

class ChartViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val barChart: BarChart = view.findViewById(R.id.bartChart)
    private val btnOrientation: ImageButton = view.findViewById(R.id.btnOrientation)

    val dataSetColors = ArrayList<Int>()

    fun bind(chartModel: ChartModel, listener: (ChartModel) -> Unit) {

        /*textViewName.text = chartModel.name
        Glide.with(imageView.context).load(chartModel.photo).into(imageView)*/
        setClickListener(listener, chartModel)
        /*setChartParams()
        setLegends()
        setXAxis()
        setYAxis()
        setData(chartModel.chartData)
        setMarkerView(view)
        setCustomRoundedBar()*/

        dataSetColors.add(rgb("#C0392B"))
        dataSetColors.add(rgb("#884EA0"))
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
                    setNegativeMultiBartData(
                        chartModel.chartType,
                        chartModel.chartData.xAxisVal,
                        chartModel.chartData.yAxisVal
                    )
                }
                "MultiBar" -> {
                    setMultiBartData(
                        chartModel.chartType,
                        chartModel.chartData.xAxisVal,
                        chartModel.chartData.yAxisVal
                    )
                }
                "Bar" -> {
                    setBartData(
                        chartModel.chartType,
                        chartModel.chartData.xAxisVal,
                        chartModel.chartData.yAxisVal
                    )
                }
                "StackBar" -> {
                    setStackBartData(
                        chartModel.chartType,
                        chartModel.chartData.xAxisVal,
                        chartModel.chartData.yAxisVal
                    )
                }
            }
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

    private fun setLegends() {
        val l: Legend = barChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(true)

        l.yOffset = 0f
        l.xOffset = 10f
        l.yEntrySpace = 0f
        l.textSize = 8f
    }

    private fun setXAxis() {
        val xAxis: XAxis = barChart.xAxis
        xAxis.position = XAxisPosition.BOTTOM

        xAxis.textColor = Color.LTGRAY
        xAxis.textSize = 13f
        xAxis.labelCount = 5
        xAxis.setCenterAxisLabels(true)
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)
        /*xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float, axis: AxisBase): String {
                return data1.get(Math.min(Math.max(value.toInt(), 0), data1.size - 1)).xAxisValue
            }
        }*/
    }

    private fun setYAxis() {
        val left: YAxis = barChart.axisLeft
        left.spaceTop = 25f
        left.spaceBottom = 25f

        left.setDrawZeroLine(true) // draw a zero line

        left.zeroLineColor = Color.GRAY
        left.zeroLineWidth = 0.7f
    }

    private fun setData(data1: List<BarChartModel>) {
        val values1 = ArrayList<BarEntry>()
        val values2 = ArrayList<BarEntry>()

        val colors: MutableList<Int> = ArrayList()
        val green = Color.rgb(110, 190, 102)
        val red = Color.rgb(211, 74, 88)

        for (i in data1.indices) {
            val d: BarChartModel = data1[i]
            val entry1 = BarEntry(d.xValue, d.yValue)
            values1.add(entry1)
            val entry2 = BarEntry(d.xValue, d.yValue + 5)
            values2.add(entry2)

            // specific colors
            if (d.yValue >= 0) colors.add(red) else colors.add(green)
        }
        val set1: BarDataSet
        val set2: BarDataSet
        if (barChart.data != null &&
            barChart.data.dataSetCount > 0
        ) {
            set1 = barChart.data.getDataSetByIndex(0) as BarDataSet
            set2 = barChart.data.getDataSetByIndex(1) as BarDataSet

            set1.values = values1
            set2.values = values2
            barChart.data.notifyDataChanged()
            barChart.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values1, "Call")
            set1.color = Color.RED
            set2 = BarDataSet(values2, "Put")
            set2.color = Color.BLUE
            val data = BarData(set1, set2)
            data.setValueTextSize(13f)
//            data.setValueTypeface(tfRegular);
//            data.setValueFormatter(ValueFormatter())
            data.barWidth = 0.8f
            barChart.data = data
        }
        set1.setDrawValues(false)
        set2.setDrawValues(false)
        val groupSpace = 0.3f
        val barSpace = 0.03f // x4 DataSet
        val barWidth = 0.2f // x4 DataSet

        barChart.barData.barWidth = barWidth
        barChart.groupBars(0f, groupSpace, barSpace)
        barChart.setVisibleXRangeMaximum(5f)
        barChart.invalidate()
    }

    private fun setMarkerView(view: View) {
        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        val mv = BarChartMarkerView(view.context, R.layout.barchart_marker_view)
        mv.chartView = barChart // For bounds control

        barChart.marker = mv // Set the marker to the chart

    }

    private fun setCustomRoundedBar() {
        val barChartRender =
            RoundBarChartRender(barChart, barChart.animator, barChart.viewPortHandler)
        barChartRender.setRadius(15)
        barChart.renderer = barChartRender
    }

    private fun setNegativeMultiBartData(
        chartTitle: String,
        xAxisVal: Array<String>,
        yAxisVal: LinkedHashMap<String, LinkedHashMap<String, FloatArray>>
    ) {
        val multiBarChart =
            BarChartWrapper().multiBarNegativeChart(
                barChart,
                xAxisVal,
                yAxisVal,
                dataSetColors,
                chartTitle
            )
        multiBarChart.invalidate()
    }

    private fun setMultiBartData(
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

    private fun setBartData(
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

    private fun setStackBartData(
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