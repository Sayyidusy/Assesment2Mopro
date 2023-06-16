package com.assesment2.mopro

import android.util.Log
import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assesment2.mopro.model.DataApi
import com.assesment2.mopro.network.ApiStatus
import com.assesment2.mopro.network.AplikasiApi
import com.assesment2.mopro.network.AplikasiApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    private val data = MutableLiveData<List<DataApi>>()
    private val status = MutableLiveData<ApiStatus>()

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            try {
                data.postValue(AplikasiApi.service.getAplikasi())
                status.postValue(ApiStatus.SUCCESS)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus.FAILED)
            }
        }
    }

    fun getData(): LiveData<List<DataApi>> = data

    fun getStatus(): LiveData<ApiStatus> = status

}