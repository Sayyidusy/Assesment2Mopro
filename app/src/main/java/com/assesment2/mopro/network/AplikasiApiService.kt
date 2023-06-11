package com.assesment2.mopro.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://sayyidusy.github.io/aplikasi_api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface AplikasiApiService {
    @GET("aplikasi_api.json")
    suspend fun getAplikasi(): String
}

object AplikasiApi {
    val service: AplikasiApiService by lazy {
        retrofit.create(AplikasiApiService::class.java)
    }
}





