package com.assesment2.mopro.network

import com.assesment2.mopro.model.DataApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/"+
        "Sayyidusy/aplikasi_api/main/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface AplikasiApiService {
    @GET("aplikasi_api.json")
    suspend fun getAplikasi(): List<DataApi>
}

object AplikasiApi {
    val service: AplikasiApiService by lazy {
        retrofit.create(AplikasiApiService::class.java)
    }
    fun getAplikasiUrl(dataImage: String): String {
        return "$BASE_URL$dataImage"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }




