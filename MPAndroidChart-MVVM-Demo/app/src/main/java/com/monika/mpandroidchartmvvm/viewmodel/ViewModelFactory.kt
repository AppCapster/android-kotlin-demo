package com.monika.mpandroidchartmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monika.mpandroidchartmvvm.model.ChartRepository

/**
 * @author Monika
 */
class ViewModelFactory(private val repository: ChartRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChartViewModel(repository) as T
    }
}