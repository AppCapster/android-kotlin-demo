package com.monika.mpandroidchartmvvm.data

import com.monika.mpandroidchartmvvm.model.*
import retrofit2.Call
import java.util.*

/**
 * @author Monika
 */
//class ChartRemoteDataSource(apiClient: ApiClient) : ChartDataSource {
class ChartRemoteDataSource() : ChartDataSource {
    private var call: Call<ChartResponse>? = null
//    private val service = apiClient.build()

    override fun retrieveCharts(callback: OperationCallback<ChartModel>) {

        val chartResponse: ChartResponse = getDummyData()
        callback.onSuccess(chartResponse.data)

        /*call = service.getChartData()
        call?.enqueue(object : Callback<ChartResponse> {
            override fun onFailure(call: Call<ChartResponse>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<ChartResponse>,
                response: Response<ChartResponse>
            ) {
                response.body()?.let {
                    if (response.isSuccessful && (it.isSuccess())) {
                        callback.onSuccess(it.data)
                    } else {
                        callback.onError(it.msg)
                    }
                }
            }
        })*/
    }

    override fun cancel() {
        call?.cancel()
    }

    private fun getDummyData(): ChartResponse {

        val chartModels: ArrayList<ChartModel> = ArrayList<ChartModel>()

        chartModels.add(ChartModel("NegativeBar", getNegativeMultiBarChart()))
        chartModels.add(ChartModel("MultiBar", getMultiBarChart()))
        chartModels.add(ChartModel("Bar", getBarChart()))
        chartModels.add(ChartModel("StackBar", getStackBarChart()))

        return ChartResponse(200, "Success", chartModels)
    }

    private fun getNegativeMultiBarChart(): BarChartWrapperModel {

        val xAxisVal = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )
        val yAxisVal1 =
            floatArrayOf(-1.5f, 5.4f, -4f, 8f, -4.3f, 5.1f, -7.6f, 6.7f, 8.9f, -4.8f, 12.1f, 3.5f)
        val yAxisVal2 =
            floatArrayOf(-10f, 11f, -13f, 12f, -14f, 9f, 4f, 2f, 8f, 25f, 13f, -2f)

        val linkedHashMap = LinkedHashMap<String, FloatArray>()
        linkedHashMap["Set"] = yAxisVal1 //String, float[]
        linkedHashMap["Put"] = yAxisVal2 //String, float[]

        val yAxisVal = LinkedHashMap<String, LinkedHashMap<String, FloatArray>>()
        yAxisVal["yAxisVal"] = linkedHashMap

        return BarChartWrapperModel(
            "NegativeBar",
            "Negative Bar", xAxisVal, yAxisVal
        )
    }

    private fun getBarChart(): BarChartWrapperModel {
        val xAxisVal = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )
        val yAxisVal1 =
            floatArrayOf(1.5f, 5.4f, 4f, 8f, 4.3f, 5.1f, 7.6f, 6.7f, 8.9f, 4.8f, 12.1f, 3.5f)
        val yAxisVal2 =
            floatArrayOf(10f, 11f, 13f, 12f, 14f, 9f, 4f, 2f, 8f, 25f, 13f, 2f)

        val linkedHashMap = LinkedHashMap<String, FloatArray>()
        linkedHashMap["Set"] = yAxisVal1 //String, float[]
        linkedHashMap["Put"] = yAxisVal2 //String, float[]

        val yAxisVal = LinkedHashMap<String, LinkedHashMap<String, FloatArray>>()
        yAxisVal["yAxisVal"] = linkedHashMap

        return BarChartWrapperModel("Bar", "Bar", xAxisVal, yAxisVal)
    }

    private fun getStackBarChart(): BarChartWrapperModel {
        val yAxisValName: String = ("Units")
        val xAxisVal = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )
        val yAxisValSize1 =
            floatArrayOf(2f, 1f, 4f, 5f, 7f, 9f, 4f, 5f, 7f, 6f, 7f, 5f)
        val yAxisValSize3 =
            floatArrayOf(10f, 2f, 4f, 5f, 7f, 9f, 4f, 2f, 7f, 12f, 14f, 1f)

        val yAxisVal2 =
            floatArrayOf(10f, 11f, 13f, 12f, 14f, 9f, 4f, 2f, 8f, 25f, 13f, 2f)

        val yAxisVal = LinkedHashMap<String, LinkedHashMap<String, FloatArray>>()

        val hashMap1 = LinkedHashMap<String, FloatArray>()
        hashMap1[yAxisValName + 1] = yAxisValSize1 //String, float[]
        hashMap1[yAxisValName + 2] = yAxisVal2 //String, float[]
        hashMap1[yAxisValName + 3] = yAxisValSize1 //String, float[]
        yAxisVal["Category1"] = hashMap1

        val hashMap2 = LinkedHashMap<String, FloatArray>()
        hashMap2[yAxisValName + 1] = yAxisVal2 //String, float[]
        hashMap2[yAxisValName + 2] = yAxisValSize3 //String, float[]
        hashMap2[yAxisValName + 3] = yAxisValSize1 //String, float[]
        yAxisVal["Category2"] = hashMap2

        val hashMap3 = LinkedHashMap<String, FloatArray>()
        hashMap3[yAxisValName + 1] = yAxisVal2 //String, float[]
        hashMap3[yAxisValName + 2] = yAxisValSize3 //String, float[]
        hashMap3[yAxisValName + 3] = yAxisValSize1 //String, float[]
        yAxisVal["Category3"] = hashMap3

        return BarChartWrapperModel("StackBar", "Stack Bar", xAxisVal, yAxisVal)
    }

    private fun getMultiBarChart(): BarChartWrapperModel {
        val xAxisVal = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )
        val yAxisVal1 =
            floatArrayOf(1.5f, 5.4f, 4f, 8f, 4.3f, 5.1f, 7.6f, 6.7f, 8.9f, 4.8f, 12.1f, 3.5f)
        val yAxisVal2 =
            floatArrayOf(10f, 11f, 13f, 12f, 14f, 9f, 4f, 2f, 8f, 25f, 13f, 2f)

        val linkedHashMap = LinkedHashMap<String, FloatArray>()
        linkedHashMap["Set"] = yAxisVal1 //String, float[]
        linkedHashMap["Put"] = yAxisVal2 //String, float[]

        val yAxisVal = LinkedHashMap<String, LinkedHashMap<String, FloatArray>>()
        yAxisVal["yAxisVal"] = linkedHashMap

        return BarChartWrapperModel("MultiBar", "Multi Bar", xAxisVal, yAxisVal)
    }

    private fun getNegativeBarChart(): ArrayList<BarChartModel> {
        val barChartModel: ArrayList<BarChartModel> = ArrayList<BarChartModel>()

        barChartModel.add(BarChartModel(0f, -10f, "12-29"))
        barChartModel.add(BarChartModel(1f, 20f, "12-30"))
        barChartModel.add(BarChartModel(2f, -30f, "12-31"))
        barChartModel.add(BarChartModel(3f, -40f, "01-01"))
        barChartModel.add(BarChartModel(4f, 50f, "01-02"))

        return barChartModel
    }

}