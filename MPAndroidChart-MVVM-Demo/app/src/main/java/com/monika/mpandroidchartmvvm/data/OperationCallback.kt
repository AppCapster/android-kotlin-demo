package com.monika.mpandroidchartmvvm.data

/**
 * @author Monika
 */
interface OperationCallback<T> {
    fun onSuccess(data: List<T>?)
    fun onError(error: String?)
}