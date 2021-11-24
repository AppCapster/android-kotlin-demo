package com.monika.mpandroidchartmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.monika.mpandroidchartmvvm.data.OperationCallback
import com.monika.mpandroidchartmvvm.model.ChartModel
import com.monika.mpandroidchartmvvm.model.ChartRepository

/**
 * @author Monika
 */
class ChartViewModel(private val repository: ChartRepository) : ViewModel() {

    private val _charts = MutableLiveData<List<ChartModel>>().apply { value = emptyList() }
    val charts: LiveData<List<ChartModel>> = _charts

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    /*
    If you require that the data be loaded only once, you can consider calling the method
    "loadcharts()" on constructor. Also, if you rotate the screen, the service will not be called.

    init {
        //loadcharts()
    }
     */

    fun loadCharts() {
        _isViewLoading.value = true
        repository.fetchCharts(object : OperationCallback<ChartModel> {
            override fun onError(error: String?) {
                _isViewLoading.value = false
                _onMessageError.value = error
            }

            override fun onSuccess(data: List<ChartModel>?) {
                _isViewLoading.value = false
                if (data.isNullOrEmpty()) {
                    _isEmptyList.value = true

                } else {
                    _charts.value = data
                }
            }
        })
    }

    fun onChartClickAction(it: ChartModel) {
        print(it.chartType)
    }

}