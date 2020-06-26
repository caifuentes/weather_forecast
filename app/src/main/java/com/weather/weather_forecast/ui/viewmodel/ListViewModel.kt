package com.weather.weather_forecast.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.weather.weather_forecast.data.api.Result
import com.weather.weather_forecast.data.repositories.ListRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ListViewModel @Inject constructor(private val repository: ListRepository) : ViewModel() {

    fun getList(ids: String) = liveData(Dispatchers.IO) {
        emit(Result.loading(data = null))

        try {
            val responseStatus = repository.getList(ids)
            if (responseStatus.status == Result.Status.SUCCESS) {
                emit(Result.success(data = responseStatus.data))
            } else {
                emit(Result.error(data = null, message = responseStatus.message ?: "Error Occurred!"))
            }
        } catch (exception: Exception) {
            emit(Result.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}