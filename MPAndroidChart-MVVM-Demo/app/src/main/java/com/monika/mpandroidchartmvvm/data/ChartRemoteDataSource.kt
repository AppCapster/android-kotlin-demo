package com.monika.mpandroidchartmvvm.data

import com.monika.mpandroidchartmvvm.model.BarChartModel
import com.monika.mpandroidchartmvvm.model.ChartDataSource
import com.monika.mpandroidchartmvvm.model.ChartModel
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
        val barChartModel: ArrayList<BarChartModel> = ArrayList<BarChartModel>()

        barChartModel.add(BarChartModel(0f, -10f, "12-29"))
        barChartModel.add(BarChartModel(1f, 20f, "12-30"))
        barChartModel.add(BarChartModel(2f, -30f, "12-31"))
        barChartModel.add(BarChartModel(3f, -40f, "01-01"))
        barChartModel.add(BarChartModel(4f, 50f, "01-02"))

        val chartModels: ArrayList<ChartModel> = ArrayList<ChartModel>()
        chartModels.add(ChartModel("NegativeBar", barChartModel))

        return ChartResponse(200, "Success", chartModels)
    }
}