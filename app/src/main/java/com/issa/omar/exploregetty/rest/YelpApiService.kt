package com.issa.omar.exploregetty.rest

import com.issa.omar.exploregetty.model.BusinessDetails
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface YelpApiService {

    companion object {
        private const val BASE_URL: String = "https://api.yelp.com/v3/"

        fun create(): YelpApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(YelpApiService::class.java)
        }
    }

    @GET("businesses/{id}")
    fun getDetails(@Header("Authorization") auth: String, @Path("id") id: String): Observable<BusinessDetails>
}