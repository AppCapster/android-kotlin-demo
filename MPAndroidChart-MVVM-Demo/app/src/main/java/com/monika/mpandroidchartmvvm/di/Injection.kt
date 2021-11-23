package com.monika.mpandroidchartmvvm.di

import com.monika.mpandroidchartmvvm.data.ApiClient
import com.monika.mpandroidchartmvvm.data.ChartRemoteDataSource
import com.monika.mpandroidchartmvvm.model.ChartDataSource
import com.monika.mpandroidchartmvvm.model.ChartRepository
import com.monika.mpandroidchartmvvm.viewmodel.ViewModelFactory

/**
 * @author Monika
 */
object Injection {

    private var chartDataSource: ChartDataSource? = null
    private var chartRepository: ChartRepository? = null
    private var chartViewModelFactory: ViewModelFactory? = null

    private fun createChartDataSource(): ChartDataSource {
        val dataSource = ChartRemoteDataSource()
        chartDataSource = dataSource
        return dataSource
    }

    private fun createChartRepository(): ChartRepository {
        val repository = ChartRepository(provideDataSource())
        chartRepository = repository
        return repository
    }

    private fun createFactory(): ViewModelFactory {
        val factory = ViewModelFactory(providerRepository())
        chartViewModelFactory = factory
        return factory
    }

    private fun provideDataSource() = chartDataSource ?: createChartDataSource()
    private fun providerRepository() = chartRepository ?: createChartRepository()

    fun provideViewModelFactory() = chartViewModelFactory ?: createFactory()

    fun destroy() {
        chartDataSource = null
        chartRepository = null
        chartViewModelFactory = null
    }
}